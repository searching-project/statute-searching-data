package com.example.precedent.precList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RequiredArgsConstructor
@Service
public class PrecListCrawling {

    // tag값 내에 담긴 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public void getDataFromXml(String target, int page) {
        try{
            // parsing할 url 만들기
            String url_material = "https://www.law.go.kr/DRF/lawSearch.do";
            String oc = "m_6595";
            String resultType = "XML";
            int display = 100;

            String url = url_material+"?OC="+oc+"&target="+target+"&type="+resultType+"&display="+display+"&page="+page;

            System.out.println(url);

            // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(url);

            // root tag
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" +doc.getDocumentElement().getNodeName());

            // 파싱할 데이터를 감싸고 있는 태그 이름을 넣고,
            // 파싱할 데이터 목록을 nList에 넣기
            NodeList nList = doc.getElementsByTagName("prec");
            System.out.println("파싱할 리스트 수 : "+ nList.getLength());

            // 파싱할 데이터들이 담긴 nList 반복문 돌리기
            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){

                    Element eElement = (Element) nNode;
                    System.out.println("######################");
                    System.out.println("판례일련번호 : " + getTagValue("판례일련번호", eElement));
                    System.out.println("사건명  : " + getTagValue("사건명", eElement));
                    System.out.println("사건번호 : " + getTagValue("사건번호", eElement));
                    System.out.println("선고일자  : " + getTagValue("선고일자", eElement));
                    System.out.println("법원명  : " + getTagValue("법원명", eElement));
                    System.out.println("법원종류코드  : " + getTagValue("법원종류코드", eElement));
                    System.out.println("사건종류명 : " + getTagValue("사건종류명", eElement));
                    System.out.println("사건종류코드  : " + getTagValue("사건종류코드", eElement));
                    System.out.println("판결유형  : " + getTagValue("판결유형", eElement));
                    System.out.println("선고  : " + getTagValue("선고", eElement));
                    System.out.println("판례상세링크  : " + getTagValue("판례상세링크", eElement));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

