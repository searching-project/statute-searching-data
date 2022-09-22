package com.example.precedent;

// 판례일련번호 List 가져오기

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrecCommandLineRunner implements CommandLineRunner {

    @Autowired
    PrecListCrawling precListCrawling;

    @Autowired
    PrecDetailCrawling precDetailCrawling;

    @Override
    public void run(String...args) throws Exception {

        List<String> precSNList;

        System.out.println("CommandLineRunner");
        precSNList = precListCrawling.getPrecSNList("prec", 84131);
        precDetailCrawling.postPrecDetails(precSNList);
    }
}