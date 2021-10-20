package com.sungcor.exam.controller;

import com.sungcor.exam.entity.Server;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.MapUtil;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/insertData")
public class ServerController {
    @Autowired
    private ServerService serverService;

//    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    @GetMapping("/insert")
    public String insertToDatabase() throws Exception {
        return serverService.insertEntity();
    }

    @GetMapping("/getAll")
    public List<Server> serverList(){
        return serverService.serverList();
    }

    @GetMapping("/getOnlineCount")
    public Map<String,Integer> getOnlineCount(){
        return serverService.getServerOnline();
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

    @GetMapping("/getOffLine")
    public Map<String,Integer> getOffLineCount(){
        List<Server> servers = serverService.serverList();
        Map<String,Integer> descByOffLine = new HashMap<>();
        for(Server server:servers){
            descByOffLine.put(server.getId(),server.getOffLineCount());
        }
        return MapUtil.sortByValueDescending(descByOffLine);
    }
}
