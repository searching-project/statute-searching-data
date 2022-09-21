package com.example.pracrawling;

import com.example.pracrawling.entity.Law;
import com.example.pracrawling.repository.LawRepository;
import com.example.pracrawling.service.LawService;
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

import static com.example.pracrawling.PublicMethod.ObjectsToJSonArray;
import static com.example.pracrawling.PublicMethod.getOptional;

@Service
@RequiredArgsConstructor
public class LawCrawlingService {
    public final LawRepository lawRepository;
    private final LawService lawService;
    private final String baseURL = "https://www.law.go.kr";
    @Value("${law.oc}")
    String OC;

    @Transactional
    public String getSimpleList(int page) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(baseURL);
        sb.append("/DRF/lawSearch.do?");
        sb.append("OC=" + OC);
        sb.append("&target=law&type=XML");
        sb.append("&page=" + page);
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
        String returnLine;
        while ((returnLine = bufferedReader.readLine()) != null) {
            result.append(returnLine);
        }

        JSONObject jsonObject = XML.toJSONObject(result.toString());
        JSONObject lawSearch = (JSONObject) jsonObject.get("LawSearch");
        JSONArray laws = lawSearch.getJSONArray("law");
        for (int i = 0; i < laws.length(); i++) {
            JSONObject law = (JSONObject) laws.get(i);

            int id = (int) law.get("id");
            law.get("법령일련번호");
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

//            System.out.println(id + " " + serialNumber + " " + link);
            link = link.replace("HTML", "XML");
            System.out.println(getDetail(baseURL + link));
        }


        return jsonPrintString = jsonObject.toString();


    }

    @Transactional
    public String getDetail(String u) throws IOException {
        URL url = new URL(u);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;

        BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
        String returnLine;
        while ((returnLine = bufferedReader.readLine()) != null) {
            result.append(returnLine);
        }

        JSONObject jsonObject = XML.toJSONObject(result.toString());
        JSONObject lawSearch = (JSONObject) jsonObject.get("법령");

        Set<String> keys = lawSearch.keySet();
        JSONObject laws = lawSearch.getJSONObject("기본정보");
        JSONObject laws1 = lawSearch.getJSONObject("조문");
        JSONObject laws2 = lawSearch.getJSONObject("부칙");
        JSONObject laws3 = keys.contains("개정문") ? lawSearch.getJSONObject("개정문") : null;
        JSONObject laws4 = keys.contains("제개정이유") ? lawSearch.getJSONObject("제개정이유") : null;

        LawDetailDto.BasicInfo basicInfo = getBasicInfo(laws);
        LawDetailDto.Article article = getArticle(laws1);
        LawDetailDto.Addendum addendum = getAddendum(laws2);
        LawDetailDto.Amendment amendment = laws3 != null ? getAmendment(laws3) : null;
        LawDetailDto.ReasonOfRevision reasonOfRevision = laws4 != null ? getReasonOfRevision(laws4) : null;

        LawDetailDto lawDetailDto = LawDetailDto.builder()
                .key((String) getOptional(lawSearch.keySet(),"법령키",lawSearch))
                .basicInfo(basicInfo)
                .article(article)
                .addendum(addendum)
                .amendment(amendment)
                .reasonOfRevision(reasonOfRevision)
                .build();


        return jsonPrintString = lawDetailDto.toString();

    }

    private LawDetailDto.ReasonOfRevision getReasonOfRevision(JSONObject laws) {
        LawDetailDto.ReasonOfRevision reasonOfRevision = new LawDetailDto.ReasonOfRevision();
        return reasonOfRevision.update(laws);
    }

    private LawDetailDto.Amendment getAmendment(JSONObject laws) {
        LawDetailDto.Amendment amendment = new LawDetailDto.Amendment();
        return amendment.update(laws);
    }

    private LawDetailDto.Addendum getAddendum(JSONObject laws) {
        LawDetailDto.Addendum addendum = new LawDetailDto.Addendum();
        return addendum.update(laws);
    }

    private LawDetailDto.Article getArticle(JSONObject laws) {
        LawDetailDto.Article article = new LawDetailDto.Article();
        return article.update(laws);
    }

    public LawDetailDto.BasicInfo getBasicInfo(JSONObject law) {
        LawDetailDto.BasicInfo basicInfo = new LawDetailDto.BasicInfo();
        return basicInfo.update(law);
    }

}

