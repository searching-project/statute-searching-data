package com.example.pracrawling;

import com.example.pracrawling.entity.Law;
import com.example.pracrawling.entity.LawMinistry;
import com.example.pracrawling.entity.Ministry;
import com.example.pracrawling.parsing.LawComponentsParsing;
import com.example.pracrawling.repository.LawMinistryRepository;
import com.example.pracrawling.repository.LawRepository;
import com.example.pracrawling.repository.MinistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static com.example.pracrawling.parsing.LawComponentsParsing.getTagValue;

@RequiredArgsConstructor
@Service
public class LawMinistryMappingService {

    private final LawRepository lawRepository;
    private final MinistryRepository ministryRepository;
    private final LawComponentsParsing lawComponentsParsing;
    private final LawMinistryRepository lawMinistryRepository;

    @Value("${law.oc}")
    String OC;

    public void mapLawMinistry() {
        try{
            int nowMappingIndex = 0;
            List<Law> lawList = lawRepository.findAll();

            for (Law law : lawList) {
                // mapping할 법령 url
                String url = "https://www.law.go.kr/DRF/lawService.do?OC=" + OC + "&target=law&ID=" + law.getLawSN() + "&type=XML&characterSetResults=UTF-8";

                // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                System.out.println("파싱 진행 상황 - " + (nowMappingIndex+1) + "/" + lawList.size() + ", 현재 파싱중인 url :" + url + "-------------------------------------------------------------------------------------------------------------------------");

                // 소관부처 저장하기
                System.out.println(postMinistries(doc, law));
                nowMappingIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String postMinistries(Document doc, Law law) {

        NodeList nMinList = doc.getElementsByTagName("부서단위");
        if (nMinList.getLength() == 0) {
            return null;
        }

        // 파싱할 데이터들이 담긴 nAddList 반복문 돌리기
        int cnt = 0;
        for (int i = 0; i < nMinList.getLength(); i++) {
            Node nMinNode = nMinList.item(i);
            if (nMinNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eMinElement = (Element) nMinNode;

                // 부서명 내의 정보만 가져오기
                String minName = getTagValue("부서명", eMinElement);

                // 부서 태그가 멀쩡히 잘 있다면
                if (minName != null) {

                    // 현재 찾은 부서가 이미 DB에 있는지 판별
                    Ministry isPresent = lawComponentsParsing.isPresentMinistry(minName);

                    // 1. 소관부처가 현재 DB에 저장되어있지 않을 때
                    if (isPresent == null) {
                        Ministry newMinistry = Ministry.builder()
                                .name(getTagValue("소관부처명", eMinElement))
                                .code(getTagValue("소관부처코드", eMinElement))
                                .department(getTagValue("부서명", eMinElement))
                                .departmentTel(getTagValue("부서연락처", eMinElement))
                                .build();

                        ministryRepository.save(newMinistry);
                        lawMinistryRepository.save(new LawMinistry(law, newMinistry));
                        cnt++;

                    }

                    // 2. 소관부처가 현재 DB에 이미 있을 때
                    else {
                        lawMinistryRepository.save(new LawMinistry(law, isPresent));
                        cnt++;
                    }
                }
            }
        }
        return "법령 ID : " + law.getLawSN() + "에 소관부처" + cnt + "개 연결 완료 ------------------------------------------------------------";
    }
}
