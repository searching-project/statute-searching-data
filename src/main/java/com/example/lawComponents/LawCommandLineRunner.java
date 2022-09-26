package com.example.lawComponents;

import com.example.lawComponents.addendum.Addendum;
import com.example.lawComponents.addendum.AddendumParsing;
import com.example.lawComponents.ministry.MinistryParsing;
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

    @Override
    public void run(String...args) throws Exception {

        // 법령 id들 가져오기
        List<String> lawList;
        lawList = lawListParsing.getLawSNList("law", 5232);

        // 소관부처 DB 작업
//        ministryParsing.postMinistries(lawList);

        // 부칙 DB 작업
        addendumParsing.postAddendums(lawList);
    }
}
