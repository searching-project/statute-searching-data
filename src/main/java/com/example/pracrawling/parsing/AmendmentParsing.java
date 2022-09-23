package com.example.pracrawling.parsing;

import com.example.pracrawling.repository.AmendmentRepository;
import com.example.pracrawling.entity.Amendment;
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
public class AmendmentParsing {
    private final AmendmentRepository amendmentRepository;

    public void postAmendments(List<String> lawSNList) {
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

                // 제개정구분 파싱
                NodeList nAmendTypeList = doc.getElementsByTagName("기본정보");
                Node nAmendTypeNode = nAmendTypeList.item(0);

                // 제개정 내용 파싱
                NodeList nAmendList = doc.getElementsByTagName("개정문");
                Node nAmendNode = nAmendList.item(0);

                // 제개정 이유 내용 파싱
                NodeList nAmendReasonList = doc.getElementsByTagName("제개정이유");
                Node nAmendReasonNode = nAmendReasonList.item(0);

                // 제개정구분 추출
                String amendType = null;
                if (nAmendTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eAmendTypeElement = (Element) nAmendTypeNode;
                    amendType = getTagValue("제개정구분", eAmendTypeElement);
                }

                // 개정문내용 추출
                String content = null;
                if (nAmendNode != null) {
                    if (nAmendNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eAmendElement = (Element) nAmendNode;
                        content = getTagValue("개정문내용", eAmendElement);
                    }
                }

                // 제개정이유내용 추출
                String reasonContent = null;
                if (nAmendReasonNode != null) {
                    if (nAmendReasonNode.getNodeType() == Node.ELEMENT_NODE){
                        Element eAmendReasonElement = (Element) nAmendReasonNode;
                        reasonContent = getTagValue("제개정이유내용", eAmendReasonElement);
                    }
                }

                Amendment amendment = Amendment.builder()
                        .type(amendType)
                        .content(content)
                        .reasonContent(reasonContent)
                        .lawId(lawSN)
                        .build();

                amendmentRepository.save(amendment);

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

        if (tag.equals("개정문내용")||tag.equals("제개정이유내용")) {
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
