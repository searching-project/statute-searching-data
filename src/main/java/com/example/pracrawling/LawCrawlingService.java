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
        }


        return jsonPrintString = jsonObject.toString();



    }
}
