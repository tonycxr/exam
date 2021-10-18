package com.sungcor.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@SpringBootApplication
@MapperScan("com.sungcor.exam.mapping")
public class ExamforsungcorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamforsungcorApplication.class, args);
    }

}
