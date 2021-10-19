package com.sungcor.exam.controller;


import com.sungcor.exam.entity.Server;
import com.sungcor.exam.redis.RedisUtil;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("insert")
//    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
    public boolean setDataRedis() throws Exception {
        StringToClass stringToClass = new StringToClass();
        List<Server> serverList = stringToClass.getServer();
        String keys="server:basic.info*";
        Set<String> keysList = redisUtil.keys(keys);
        for(String str:keysList){
            redisUtil.del(str);
        }
        String key;
        for(Server server:serverList){
            key = "server:basic.info:{"+server.getId()+"}:List";
            redisUtil.set(key,server);
        }
        return true;
    }
}
