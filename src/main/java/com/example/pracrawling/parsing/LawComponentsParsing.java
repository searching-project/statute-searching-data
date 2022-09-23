//package com.example.pracrawling.parsing;
//
//import com.example.pracrawling.entity.Addendum;
//import com.example.pracrawling.entity.Amendment;
//import com.example.pracrawling.entity.Law;
//import com.example.pracrawling.entity.Ministry;
//import com.example.pracrawling.repository.AddendumRepository;
//import com.example.pracrawling.repository.AmendmentRepository;
//import com.example.pracrawling.repository.LawRepository;
//import com.example.pracrawling.repository.MinistryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class LawComponentsParsing {
//
//    @Autowired
//    private final MinistryRepository ministryRepository;
//
//    @Autowired
//    private final AmendmentRepository amendmentRepository;
//
//    @Autowired
//    private final AddendumRepository addendumRepository;
//
//    @Autowired
//    private final LawRepository lawRepository;
//
//    public void postLawComponentsOnDB(List<String> lawSNList) {
//        try {
//
//            List<String> errorIds = new ArrayList<>();
//            int nowParsing = 1;
//
//            for (String lawSN : lawSNList) {
//
//                // parsing할 url 만들기
//                String url_material = "https://www.law.go.kr/DRF/lawService.do";
//                String oc = "m_6595";
//                String target = "law";
//                String resultType = "XML";
//
//                String url = url_material + "?OC=" + oc + "&target=" + target + "&ID=" + lawSN + "&type=" + resultType;
//                System.out.println("파싱 진행 상황 - " + nowParsing + "/" + lawSNList.size() + ", 현재 파싱중인 url :" + url);
//
//                // XML 문서에서 DOM 오브젝트 트리를 생성하는 parser 얻기
//                Document doc = DocumentBuilderFactory
//                        .newInstance()
//                        .newDocumentBuilder()
//                        .parse(url);
//
//                // root tag
//                doc.getDocumentElement().normalize();
//
////                // 부칙이 참조하는 법령 찾기
////                Law law = isPresentLaw(lawSN);
//
//                // 소관부처 저장하기
//                String minResult = postMinistries(doc);
//                if (minResult == null) {
//                    errorIds.add(lawSN);
//                    nowParsing++;
//                    continue;
//                } else {
//                    System.out.println(minResult);
//                }
//
////                // 부칙 저장하기
////                String addResult = postAddendums(doc, law);
////                if (addResult == null) {
////                    errorIds.add(lawSN);
////                    nowParsing++;
////                    continue;
////                } else {
////                    System.out.println(addResult);
////                }
////
////                // 제개정구문 저장하기
////                String amendResult = postAmendments(doc, law);
////                if (amendResult == null) {
////                    errorIds.add(lawSN);;
////                } else {
////                    System.out.println(amendResult);
////                }
//
//                nowParsing++;
//            }
//            System.out.println("부서 데이터 모두 파싱 완료" + "찾을 수 없는 법령 id :" + errorIds);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 법령 - 소관부처 파싱하야
//    private String postMinistries(Document doc) {
//
//        NodeList nMinList = doc.getElementsByTagName("연락부서");
//        Node nMinNode = nMinList.item(0);
//        if (nMinNode.getNodeType() == Node.ELEMENT_NODE) {
//            Element eMinElement = (Element) nMinNode;
//
//            // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
//            if (eMinElement.getNodeValue() == null) {
//                return null;
//            }
//
//            // 이미 있는 부서인지 확인
//            String minName = getTagValue("부서명", eMinElement);
//            if (isPresentMinistry(minName) == null) {
//                Ministry ministry = Ministry.builder()
//                        .name(getTagValue("소관부처명", eMinElement))
//                        .code(getTagValue("소관부처코드", eMinElement))
//                        .department(getTagValue("부서명", eMinElement))
//                        .departmentTel(getTagValue("부서연락처", eMinElement))
//                        .build();
//
//                ministryRepository.save(ministry);
//            }
//        }
//        return "소관부처 저장 완료";
//    }
//
//    // 법령 - 부칙 정보 파싱하여 DB에 저장하기
//    private String postAddendums(Document doc, Law law) {
//
//        NodeList nAddList = doc.getElementsByTagName("부칙단위");
//
//        // 파싱할 데이터들이 담긴 nAddList 반복문 돌리기
//        for (int temp = 0; temp < nAddList.getLength(); temp++) {
//            Node nAddNode = nAddList.item(0);
//            if (nAddNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eAddElement = (Element) nAddNode;
//
//                // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
//                if (eAddElement.getNodeValue() == null) {
//                    return null;
//                }
//
//                // 부칙 build하기
//                Addendum addendum = Addendum.builder()
//                        .publishDate(getTagValue("부칙공포일자", eAddElement))
//                        .publishNumber(getTagValue("부칙공포번호", eAddElement))
//                        .content(getTagValue("부칙내용", eAddElement))
//                        .law(law)
//                        .build();
//
//                addendumRepository.save(addendum);
//            }
//        }
//        return "부칙 저장 완료";
//    }
//
//    // 법령 - 제개정 부분 파싱하여 DB에 저장하기
//    private String postAmendments(Document doc, Law law) {
//        NodeList nAmendTypeList = doc.getElementsByTagName("기본정보");
//        Node nAmendTypeNode = nAmendTypeList.item(0);
//
//        // 제개정 내용 파싱
//        NodeList nAmendList = doc.getElementsByTagName("개정문");
//        Node nAmendNode = nAmendList.item(0);
//
//        // 제개정 이유 내용 파싱
//        NodeList nAmendReasonList = doc.getElementsByTagName("제개정이유");
//        Node nAmendReasonNode = nAmendReasonList.item(0);
//
//        // 제개정구분 추출
//        String amendType = null;
//        if (nAmendTypeNode.getNodeType() == Node.ELEMENT_NODE) {
//            Element eAmendTypeElement = (Element) nAmendTypeNode;
//
//            // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
//            if (eAmendTypeElement.getNodeValue() == null) {
//                return null;
//            }
//            amendType = getTagValue("제개정구분", eAmendTypeElement);
//        }
//
//        // 개정문내용 추출
//        String content = null;
//        if (nAmendNode != null) {
//            if (nAmendNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eAmendElement = (Element) nAmendNode;
//
//                // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
//                if (eAmendElement.getNodeValue() == null) {
//                    return null;
//                }
//
//                content = getTagValue("개정문내용", eAmendElement);
//            }
//        }
//
//        // 제개정이유내용 추출
//        String reasonContent = null;
//        if (nAmendReasonNode != null) {
//            if (nAmendReasonNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eAmendReasonElement = (Element) nAmendReasonNode;
//
//                // 특이사항시 반복문 넘기기 (ID가 잘못된 경우)
//                if (eAmendReasonElement.getNodeValue() == null) {
//                    return null;
//                }
//
//                reasonContent = getTagValue("제개정이유내용", eAmendReasonElement);
//            }
//        }
//
//        Amendment amendment = Amendment.builder()
//                .type(amendType)
//                .content(content)
//                .reasonContent(reasonContent)
//                .law(law)
//                .build();
//
//        amendmentRepository.save(amendment);
//        return "제개정문 저장 완료";
//    }
//
//    // tag값(<>) 안의 정보를 가져오는 메소드
//    private static String getTagValue(String tag, Element eElement) {
//        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
//
//        if (tag.equals("개정문내용") || tag.equals("제개정이유내용") || tag.equals("부칙내용")) {
//            StringBuilder nValueString = new StringBuilder();
//            for (int i = 0; i <= nlList.getLength(); i++) {
//                Node nValue = (Node) nlList.item(i);
//                if (nValue == null) {
//                    nValueString.append("\n");
//                    continue;
//                }
//                nValueString.append(nValue.getNodeValue());
//            }
//            return nValueString.toString();
//        }
//
//        Node nValue = (Node) nlList.item(0);
//        if (nValue == null)
//            return null;
//        return nValue.getNodeValue();
//    }
//
//    private Law isPresentLaw(String lawSN) {
//        Optional<Law> law = lawRepository.findById(lawSN);
//        return law.orElse(null);
//    }
//
//    private Ministry isPresentMinistry(String departmentName) {
//        Optional<Ministry> presentMinistry = ministryRepository.findByDepartment(departmentName);
//        return presentMinistry.orElse(null);
//    }
//}
