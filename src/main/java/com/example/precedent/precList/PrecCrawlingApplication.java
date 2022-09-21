package com.example.precedent.precList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrecCrawlingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrecCrawlingApplication.class, args);
    }

    // 판례 목록 100개씩 가져오기
    @Bean
    public CommandLineRunner demo(PrecListCrawling precListCrawling) {
        return (args) -> {
            precListCrawling.getDataFromXml("prec", 1);
        };
    }
}