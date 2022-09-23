package com.example.precedentParsing;

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
public class PrecDetailParsing {

    private final PrecedentRepository precedentRepository;

    public void postPrecDetails(List<String> precSNList) {
        try {
            List<String> errorIds = new ArrayList<>();

            for (String precSN : precSNList) {

                // parsing할 url 만들기
                String url_material = "https://www.law.go.kr/DRF/lawService.do";
                String oc = "m_6595";
                String target = "prec";
                String resultType = "XML";

                String url = url_material + "?OC=" + oc + "&target=" + target + "&ID=" + precSN + "&type=" + resultType;
                System.out.println("현재 파싱중인 페이지 :" + url);

                // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
                Document doc = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                // 1. 파싱할 데이터를 감싸고 있는 태그 이름을 넣고,
                // 2. 파싱할 데이터 목록을 nList에 넣기
                NodeList nList = doc.getChildNodes();

                // 파싱할 데이터
                Node nNode = nList.item(0);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // 특이사항시 반복문 넘기기 (판례 ID가 잘못된 경우)
                    if (eElement.getTagName().equals("Law")) {
                        errorIds.add(precSN);
                        continue;
                    }

                    Precedent precCase = Precedent.builder()
                            .precSN(getTagValue("판례정보일련번호", eElement))
                            .caseName(getTagValue("사건명", eElement))
                            .caseNumber(getTagValue("사건번호", eElement))
                            .judgeDate(getTagValue("선고일자", eElement))
                            .courtName(getTagValue("법원명", eElement))
                            .courtTypeCode(getTagValue("법원종류코드", eElement))
                            .caseTypeName(getTagValue("사건종류명", eElement))
                            .caseTypeCode(getTagValue("사건종류코드", eElement))
                            .judgeType(getTagValue("판결유형", eElement))
                            .judgeState(getTagValue("선고", eElement))
                            .judgeHolding(getTagValue("판시사항", eElement))
                            .judgeReasoning(getTagValue("판결요지", eElement))
                            .refArticle(getTagValue("참조조문", eElement))
                            .refPrecedent(getTagValue("참조판례", eElement))
                            .content(getTagValue("판례내용", eElement))
                            .build();

                    precedentRepository.save(precCase);
                }

            }
            System.out.println("판례 데이터 모두 파싱 완료" + "찾을 수 없는 판례 id :" + errorIds);
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

