package com.example.precedent.precList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PrecCrawlingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrecCrawlingApplication.class, args);
    }

    // 판례일련번호 List 가져오기
    List<String> precSNList;
    @Bean
    public CommandLineRunner demo(PrecListCrawling precListCrawling) {
        return (args) -> {
            precSNList = precListCrawling.getPrecSNList("prec", 84137);
        };
    }
}