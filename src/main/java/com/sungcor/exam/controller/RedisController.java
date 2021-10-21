package com.sungcor.exam.controller;
import com.sungcor.exam.entity.Server;
import com.sungcor.exam.redis.RedisUtil;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sungcor.exam.utils.TimeUtil.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    ServerService serverService;

    @RequestMapping("insert")
//    @Scheduled(cron = "1/5 * * * * ?")
    @Scheduled(cron = "0 0/60 * * * ?")
    public boolean set24HourStatus() throws Exception {
//        StringToClass stringToClass = new StringToClass();
//        List<Server> serverList = stringToClass.getServer();
        Date date = new Date();
        String keys="server:"+changeFormat(date);
//        Set<String> keysList = redisUtil.keys(keys);
//        for(String str:keysList){
//            redisUtil.del(str);
//        }
//        String key;
//        for(Server server:serverList){
//            key = "server:basic.info:{"+server.getId()+"}:List";
//            redisUtil.set(key,server);
//        }
//        System.out.println("更新Redis数据成功"+new Date());
        Map<String,String> serverStatus = serverService.getServerStatus();
        for (Map.Entry<String, String> entry : serverStatus.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            redisUtil.set(keys+k,v);
            redisUtil.expire(keys+k,86400);
        }
        System.out.println("更新Redis数据成功"+date);
        return true;
    }
}
