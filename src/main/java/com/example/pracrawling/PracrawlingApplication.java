package com.example.pracrawling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracrawlingApplication {
    private final int exam =84131;
    private final int LIST = 5232;

    public static void main(String[] args) {
        SpringApplication.run(PracrawlingApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo( LawCrawlingService courseService) {
        return (args) -> {
            for (int i=0; i<=LIST/20+1; i++) {
                courseService.getSimpleList(i);
//                courseService.getDetail(i);
//              System.out.println(courseService.getSimpleExamList(i));
            }
        };
    }
}
