package com.example.pracrawling;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@Slf4j
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
                log.info(String.valueOf(i));
                courseService.getSimpleList(i);
//                courseService.getDetail(i);
//              System.out.println(courseService.getSimpleExamList(i));
            }
        };
    }
}
