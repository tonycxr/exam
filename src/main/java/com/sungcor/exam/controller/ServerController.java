package com.sungcor.exam.controller;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/insertData")
public class ServerController {
    @Autowired
    private ServerService serverService;

//    @Scheduled(cron = "1/5 * * * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
    @GetMapping("/get")
    public int insertToDatabase() throws Exception {
        return serverService.insertEntity();
    }

}
