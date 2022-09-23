package com.example.pracrawling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class PraCommandLineRunner implements CommandLineRunner {

    private final int exam = 84131;
    private final int LIST = 5232;

    @Autowired
    LawCrawlingService courseService;

    @Override
    public void run(String... args) throws Exception {
//        for (int i = 0; i <= LIST / 20 + 1; i++) {
//            courseService.getSimpleList(i);
//              System.out.println(courseService.getSimpleExamList(i));
              System.out.println("order1 실행완료");
//        }
    }
}
