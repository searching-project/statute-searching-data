package com.example.pracrawling;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LawCrawlingService {

    private final String baseURL= "https://www.law.go.kr";
    @Value("${law.oc}")
    String OC;
    @Transactional
    public String getSimpleList(int page) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(baseURL);
        sb.append("/DRF/lawSearch.do?");
        sb.append("OC="+OC);
        sb.append("&target=law&type=XML");
        sb.append("&page="+page);
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
        String returnLine;
        while((returnLine = bufferedReader.readLine()) != null) {
            result.append(returnLine);
        }

        JSONObject jsonObject = XML.toJSONObject(result.toString());
        JSONObject lawSearch = (JSONObject) jsonObject.get("LawSearch");
        JSONArray laws = lawSearch.getJSONArray("law");
        for(int i=0; i<laws.length();i++){
            JSONObject law = (JSONObject) laws.get(i);

            int id = (int) law.get("id");
//             (String) law.get("법령일련번호");
            law.get("현행연혁코드");
            law.get("법령명한글");
            law.get("법령약칭명");
            String serialNumber = (String) law.get("법령ID");
            law.get("공포일자");
            law.get("공포번호");
            law.get("제개정구분명");
            law.get("소관부처코드");
            law.get("소관부처명");
            law.get("법령구분명");
            law.get("시행일자");
            law.get("자법타법여부");
            String link = (String) law.get("법령상세링크");

            System.out.println(id +" "+ serialNumber +" "+ link);
            link = link.replace("HTML", "XML");
            System.out.println(getDetail(baseURL+link));
        }


        return jsonPrintString = jsonObject.toString();



    }
    @Transactional
    public String getDetail(String u) throws IOException {
        URL url = new URL(u);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");
        conn.connect();
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;

        BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
        String returnLine;
        while((returnLine = bufferedReader.readLine()) != null) {
            result.append(returnLine);
        }

        JSONObject jsonObject = XML.toJSONObject(result.toString());
        JSONObject lawSearch = (JSONObject) jsonObject.get("법령");

        Set<String> keys = lawSearch.keySet();
        JSONObject laws = lawSearch.getJSONObject("기본정보");
        JSONObject laws1 = lawSearch.getJSONObject("조문");
        JSONObject laws2 = lawSearch.getJSONObject("부칙");
        JSONObject laws3 = lawSearch.getJSONObject("개정문");
        JSONObject laws4 = keys.contains("제개정이유")?lawSearch.getJSONObject("제개정이유"):null;

        LawDetailDto.BasicInfo basicInfo = getBasicInfo(laws);
        LawDetailDto.Article article = getArticle(laws1);
        LawDetailDto.Addendum addendum = getAddendum(laws2);
        LawDetailDto.Amendment amendment = getAmendment(laws3);
        LawDetailDto.ReasonOfRevision reasonOfRevision = getReasonOfRevision(laws4);
        LawDetailDto lawDetailDto = LawDetailDto.builder()
//                .key(jsonObject.getString("법령키"))
                .basicInfo(basicInfo)
                .article(article)
                .addendum(addendum)
                .amendment(amendment)
                .reasonOfRevision(reasonOfRevision)
                .build();



        return jsonPrintString = lawDetailDto.toString();

    }

    private LawDetailDto.ReasonOfRevision getReasonOfRevision(JSONObject laws) {
        ArrayList<String> contents = new ArrayList<>();
        Object object = laws.get("제개정이유내용");
        JSONArray contentArray =ObjectsToJSonArray(object);
        for(int j=0; j<contentArray.length();j++){
            contents.add(contentArray.get(j).toString());
        }

        return LawDetailDto.ReasonOfRevision.builder()
                .content(contents)
                .build();
    }

    private LawDetailDto.Amendment getAmendment(JSONObject laws) {

        ArrayList<String> contents = new ArrayList<>();
        Object object = laws.get("개정문내용");
        JSONArray contentArray = ObjectsToJSonArray(object);

        for(int j=0; j<contentArray.length();j++){
            contents.add(contentArray.get(j).toString());
        }
        return LawDetailDto.Amendment.builder()
                .content(contents)
                .build();
    }

    private LawDetailDto.Addendum getAddendum(JSONObject laws) {
        ArrayList<LawDetailDto.Addendum.AddendumDetail> detailArrayList = new ArrayList<>();
        Object object = laws.get("부칙단위");
        if(object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            ArrayList<String> contents = new ArrayList<>();
            JSONArray contentArray = jsonObject.getJSONArray("부칙내용");
            for(int j=0; j<contentArray.length();j++){
                contents.add(contentArray.get(j).toString());
            }
            detailArrayList.add(LawDetailDto.Addendum.AddendumDetail.builder()
                    .date(jsonObject.getInt("부칙공포일자"))
                    .key(jsonObject.getLong("부칙키"))
//                            .number(jsonObject.getString("부칙공포번호"))
                    .content(contents)
                    .build());
        }else{
            JSONArray jsonArray = laws.getJSONArray("부칙단위");
            for(int i= 0; i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ArrayList<String> contents = new ArrayList<>();
                JSONArray contentArray = jsonObject.getJSONArray("부칙내용");
                for(int j=0; j<contentArray.length();j++){
                    contents.add(contentArray.get(j).toString());
                }
                detailArrayList.add(LawDetailDto.Addendum.AddendumDetail.builder()
                        .date(jsonObject.getInt("부칙공포일자"))
                        .key(jsonObject.getLong("부칙키"))
//                            .number(jsonObject.getString("부칙공포번호"))
                        .content(contents)
                        .build());
            }
        }

        return LawDetailDto.Addendum.builder()
                .details(detailArrayList)
                .build();
    }

    private LawDetailDto.Article getArticle(JSONObject laws) {
        Object object = laws.get("조문단위");
        JSONArray jsonArray = ObjectsToJSonArray(object);
        ArrayList<LawDetailDto.Article.ArticleDetail> detailArrayList = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            ArrayList<String> contents = new ArrayList<>();
            Object contentObject = jsonObject.get("조문내용");
            JSONArray contentArray = ObjectsToJSonArray(contentObject);
            for(int j=0; j<contentArray.length();j++){
                contents.add(contentArray.get(j).toString());
            }
            detailArrayList.add(LawDetailDto.Article.ArticleDetail.builder()
                    .date(jsonObject.getInt("조문시행일자"))
                    .isChanged(jsonObject.getString("조문변경여부").equals("Y")?true:false)
//                    .title(jsonObject.getString("조문제목"))
                    .isArticle(jsonObject.getString("조문여부").equals("Y")?true:false)
                    .key(jsonObject.getString("조문키"))
                    .number(jsonObject.getInt("조문번호"))
                    .moveBefore(jsonObject.getString("조문이동이전"))
                    .moveAfter(jsonObject.getString("조문이동이후"))
                    .content(contents)
                    .build());
        }
        return LawDetailDto.Article.builder()
                .details(detailArrayList)
                .build();
    }
    public JSONArray ObjectsToJSonArray(Object object){
        JSONArray objects = new JSONArray();
        if(object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            objects.put(jsonObject);
        }else if(object instanceof JSONArray){
            objects = (JSONArray) object;
        }else if(object instanceof String){
            objects.put(object);
        }
        return objects;
    }

    public LawDetailDto.BasicInfo getBasicInfo(JSONObject law){
            JSONObject contantJson;
            ArrayList<LawDetailDto.BasicInfo.Contact> contacts = new ArrayList<>();
        Object object = law.getJSONObject("연락부서").get("부서단위");
        JSONArray objects = ObjectsToJSonArray(object);

        for(int i=0;i<objects.length();i++){
            contantJson = (JSONObject) objects.get(i);
            contacts.add( LawDetailDto.BasicInfo.Contact.update(contantJson));
        }

           return LawDetailDto.BasicInfo.builder()
                    .hepaticJoint(law.getInt("편장절관"))
                    .isChange(law.getString("제명변경여부").equals("Y")?true:false)
                    .language(law.getString("언어"))
                    .isKorean(law.getString("한글법령여부").equals("Y")?true:false)
                    .revision(law.getString("제개정구분"))
                    .koreaName(law.getString("법령명_한글"))
                    .phoneNumber(law.getString("전화번호"))
                    .contact(contacts)
                    .effectiveDate(law.getInt("시행일자"))
                    .isEffective(law.getString("공포법령여부").equals("Y")?true:false)
                    .competentMinistries(LawDetailDto.BasicInfo.Ministries.builder()
                            .code(law.getJSONObject("소관부처").getInt("소관부처코드"))
                            .content(law.getJSONObject("소관부처").getString("content"))
                            .build())
                    .id(law.getString("법령ID"))
                    .number(law.getInt("공포번호"))
                    .chineseName(law.getString("법령명_한자"))
                    .classification(LawDetailDto.BasicInfo.Classification.builder()
                            .code(law.getJSONObject("법종구분").getString("법종구분코드"))
                            .content(law.getJSONObject("법종구분").getString("법종구분코드"))
                            .build())
                    .date(law.getInt("공포일자"))
                    .abbreviation(law.getString("법령명약칭"))
                    .isEdit(law.getString("별표편집여부").equals("Y")?true:false)
                    .build();
        
    }

}

