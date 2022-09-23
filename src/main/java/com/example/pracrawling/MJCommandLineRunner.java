package com.example.pracrawling;

import com.example.pracrawling.parsing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(2)
@Component
public class MJCommandLineRunner implements CommandLineRunner {

    @Autowired
    LawListParsing lawListParsing;

    @Autowired
    LawComponentsParsing lawComponentsParsing;

    @Override
    public void run(String...args) throws Exception {

        // 법령 id들 가져오기
        List<String> lawList;
        lawList = lawListParsing.getLawSNList("law", 5232);

        // 법령 관련 요소들 DB 작업 - 소관부처, 부칙, 제개정문
        lawComponentsParsing.postMinistryAndAddendumAndAmendment(lawList);
    }
}
