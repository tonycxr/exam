package com.sungcor.exam.controller;
import com.sungcor.exam.redis.RedisUtil;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.sungcor.exam.utils.TimeUtil.changeFormat;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServerService serverService;


//    @RequestMapping("insert")
////    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(fixedDelayString = "${task.data.push.fixed}")
//    public Result<?> set24HourStatus(){
//        Date date = new Date();
//        String keys="server:"+changeFormat(date);
//        Map<String,Object> serverStatus = serverService.getStatus();
//        redisUtil.set(keys,serverStatus.toString());
//        redisUtil.expire(keys,3600);
//        return Result.ok("更新Redis数据成功"+date);
//    }

    @GetMapping("/redd")
    public String testRedis(){
        redisTemplate.opsForValue().set("name","JerryCheng");
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }

    @PostMapping("/secKill")
    public boolean secKill(@RequestParam String productId){
        return serverService.doSecKill(productId);
    }
}
