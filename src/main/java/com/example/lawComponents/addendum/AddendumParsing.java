package com.example.lawComponents.addendum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddendumParsing {
    private final AddendumRepository addendumRepository;

    public void postAddendums(List<String> lawSNList) {
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

                // 부칙 파싱
                NodeList nAddList = doc.getElementsByTagName("부칙단위");

                // 파싱할 데이터들이 담긴 nAddList 반복문 돌리기
                for (int temp = 0; temp < nAddList.getLength(); temp++) {
                    Node nAddNode = nAddList.item(0);
                    if (nAddNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eAddElement = (Element) nAddNode;

                        Addendum addendum = Addendum.builder()
                                .publishDate(getTagValue("부칙공포일자", eAddElement))
                                .publishNumber(getTagValue("부칙공포번호", eAddElement))
                                .content(getTagValue("부칙내용", eAddElement))
                                .lawId(lawSN)
                                .build();

                        addendumRepository.save(addendum);
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

        if (tag.equals("부칙내용")) {
            StringBuilder nValueString = new StringBuilder();
            for (int i = 0; i <= nlList.getLength(); i++) {
                Node nValue = (Node) nlList.item(i);
                if (nValue == null) {
                    nValueString.append("\n");
                    continue;
                }
                nValueString.append(nValue.getNodeValue());
            }
            return nValueString.toString();
        }

        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}
