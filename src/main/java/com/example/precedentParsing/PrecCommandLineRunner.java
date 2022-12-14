package com.example.precedentParsing;

// 판례일련번호 List 가져오기

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrecCommandLineRunner implements CommandLineRunner {

    @Autowired
    PrecListParsing precListParsing;

    @Autowired
    PrecDetailParsing precDetailParsing;

    @Override
    public void run(String...args) throws Exception {

        List<String> precSNList;

        precSNList = precListParsing.getPrecSNList("prec", 84131);
//        precSNList = Arrays.asList("226595", "223801");
        precDetailParsing.postPrecDetails(precSNList);
    }
}