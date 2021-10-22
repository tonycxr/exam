package com.sungcor.exam.controller;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.MapUtil;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServerController {
    @Autowired
    private ServerService serverService;

//    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    @GetMapping("/insert")
    @Scheduled(cron = "0 0/30 * * * ?")
    public String insertToDatabase() throws Exception {
        serverService.updateOffLine();
        return serverService.insertEntity();
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @GetMapping("/update")
    public String updateData() throws Exception{
        System.out.println("更新数据成功"+new Date());
        return serverService.updateEntity();
    }

    @GetMapping("/getAll")
    public List<Server> serverList(){
        return serverService.serverList();
    }

    @GetMapping("/getStatus")
    public Map<String,Integer> getServerStatus(){
        return serverService.getServerStatus();
    }

    @GetMapping("/FuzzyQuery/name/{name}")
    public List<Server> getServerFuzzyName(@PathVariable String name){
        return serverService.getServerByName(name);
    }

    @GetMapping("/FuzzyQuery/ip/{ip}")
    public List<Server> getServerFuzzyIp(@PathVariable String ip){
        return serverService.getServerByIp(ip);
    }

    @GetMapping("/delete")
    public String deleteAll(){
        return serverService.deleteAll();
    }

    @GetMapping("/getOffLineCount")
    public Map<String,Integer> getOffLineCount(){
        Map<String,Integer> serverMap = serverService.serverListOff();
        return MapUtil.sortByValueDescending(serverMap);
    }
}
