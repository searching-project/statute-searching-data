package com.example.pracrawling;

import com.example.pracrawling.parsing.LawListParsing;
import com.example.pracrawling.parsing.AddendumParsing;
import com.example.pracrawling.parsing.AmendmentParsing;
import com.example.pracrawling.parsing.MinistryParsing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LawCommandLineRunner implements CommandLineRunner {

    @Autowired
    LawListParsing lawListParsing;

    @Autowired
    MinistryParsing ministryParsing;

    @Autowired
    AddendumParsing addendumParsing;

    @Autowired
    AmendmentParsing amendmentParsing;

    @Override
    public void run(String...args) throws Exception {

        // 법령 id들 가져오기
        List<String> lawList;
        lawList = lawListParsing.getLawSNList("law", 5232);

        // 소관부처 DB 작업
        ministryParsing.postMinistries(lawList);

        // 부칙 DB 작업
        addendumParsing.postAddendums(lawList);

        // 제개정문 DB 작업
        amendmentParsing.postAmendments(lawList);
    }
}
