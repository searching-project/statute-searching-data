package com.example.ministry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LawCommandLineRunner implements CommandLineRunner {

    @Autowired
    MinistryParsing ministryParsing;

    @Autowired
    LawListParsing lawListParsing;

    @Override
    public void run(String...args) throws Exception {

        List<String> lawList;

        lawList = lawListParsing.getLawSNList("law", 5232);
        ministryParsing.postMinistries(lawList);

    }
}
