package com.sungcor.exam.controller;

import com.sungcor.exam.service.DownloadInfoToMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/getServerData")
public class GetServerController {
    @Autowired
    DownloadInfoToMemory downloadInfoToMemory;

    private static final String theURLToFindServer = "http://192.168.0.28/store/openapi/v2/resources/query?apikey=e10adc3949ba59abbe56e057f2gg88dd";

    @ResponseBody
    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    @PostMapping("post")
    public void getdataList() throws IOException {

    }

}
