package com.sungcor.exam;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.mapping.ServerMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RestController
@MapperScan("com.sungcor.exam.mapping")
class ExamforsungcorApplicationTests {
    @Autowired(required = false)
    ServerMapper serverMapper;

    @Test
    void contextLoads() {

    }

}
