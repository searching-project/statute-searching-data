package com.example.lawComponents.ministry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MinistryParsing {
    private final MinistryRepository ministryRepository;

    public void postMinistries(List<String> lawSNList) {
        try {

            List<String> errorIds = new ArrayList<>();
            int nowParsing = 1;

            for (String lawSN : lawSNList) {

                // parsing할 url 만들기
                String url_material = "https://www.law.go.kr/DRF/lawService.do";
                String oc = "m_6595";
                String target = "law";
                String resultType = "XML";

                String url = url_material + "?OC=" + oc + "&target=" + target + "&ID=" + lawSN + "&type=" + resultType;
                System.out.println("파싱 진행 상황 - " + nowParsing + "/" + lawSNList.size() + ", 현재 파싱중인 url :" + url);

                // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
                Document doc = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                // 소관부처 파싱
                NodeList nMinList = doc.getElementsByTagName("연락부서");
                Node nMinNode = nMinList.item(0);
                if (nMinNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMinElement = (Element) nMinNode;

                    // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
                    if (eMinElement.getNodeValue() == null) {
                        errorIds.add(lawSN);
                        nowParsing++;
                        continue;
                    }

                    // 이미 있는 부서인지 확인
                    Optional<Ministry> presentMinistry = ministryRepository.findByDepartment(getTagValue("부서명", eMinElement));

                    if (presentMinistry.isEmpty()) {

                        Ministry ministry = Ministry.builder()
                                .name(getTagValue("소관부처명", eMinElement))
                                .code(getTagValue("소관부처코드", eMinElement))
                                .department(getTagValue("부서명", eMinElement))
                                .departmentTel(getTagValue("부서연락처", eMinElement))
                                .build();

                        ministryRepository.save(ministry);
                    }
                }
                nowParsing++;
            }
            System.out.println("부서 데이터 모두 파싱 완료" + "찾을 수 없는 법령 id :" + errorIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tag값(<>) 안의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}
