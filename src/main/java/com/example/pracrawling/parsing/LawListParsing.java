package com.example.pracrawling.parsing;

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
public class LawListParsing {

    public List<String> getLawSNList(String target, int totalCnt) {
        try {
            // 페이지 조정
            int page = 1;
            int display = 100;
            int pages = totalCnt / display + 1;
            System.out.println("법령 리스트 : 총 파싱할 페이지 :" + pages);

            // 판례일련번호를 담을 빈 리스트 생성
            List<String> lawSNList = new ArrayList<>();

            // 반복문으로 데이터 파싱하기
            while (page <= pages) {
                System.out.println("리스트 파싱 진행중 - " + page + "/" + pages + "페이지");

                // parsing할 url 만들기
                String url_material = "https://www.law.go.kr/DRF/lawSearch.do";
                String oc = "m_6595";
                String resultType = "XML";
                String url = url_material + "?OC=" + oc + "&target=" + target + "&type=" + resultType + "&display=" + display + "&page=" + page;

                // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
                Document doc = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                // 파싱할 데이터를 감싸고 있는 태그 이름을 넣고,
                // 파싱할 데이터 목록을 nList에 넣기
                NodeList nList = doc.getElementsByTagName("law");

                // 파싱할 데이터들이 담긴 nList 반복문 돌리기
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        lawSNList.add(getTagValue("법령ID", eElement));
                    }
                }
                page++;
            }
            return lawSNList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
