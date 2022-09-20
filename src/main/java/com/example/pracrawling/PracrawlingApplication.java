package com.example.pracrawling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PracrawlingApplication {
    private final int exam =84131;
    private final int LIST = 5232;

    public static void main(String[] args) {
        SpringApplication.run(PracrawlingApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo( LawCrawling courseService) {
        return (args) -> {
            for (int i=0; i<=exam/20+1; i++) {
                courseService.getSimpleList(i);
//              System.out.println(courseService.getSimpleExamList(i));
            }
        };
    }
}
