package com.example.pracrawling;

import com.example.pracrawling.parsing.LawComponentsParsing;
import com.example.pracrawling.parsing.LawListParsing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(2)
@Component
public class MJCommandLineRunner implements CommandLineRunner {

    @Autowired
    private LawListParsing lawListParsing;

    @Autowired
    private LawComponentsParsing lawComponentsParsing;

    @Autowired
    private LawMinistryMappingService lawMinistryMappingService;

    @Override
    public void run(String...args) throws Exception {

        // 법령 id 리스트 가져오기
        List<String> lawList;
        lawList = lawListParsing.getLawSNList("law", 5232);

        // 법령 관련 요소들 DB 작업 - 소관부처, 부칙, 제개정문
        lawComponentsParsing.postLawComponentsOnDB(lawList);

        // 법령과 부서 매핑하기
        lawMinistryMappingService.mapLawMinistry();

    }
}
