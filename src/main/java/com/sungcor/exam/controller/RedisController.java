package com.sungcor.exam.controller;
import com.sungcor.exam.redis.RedisUtil;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.Result;
import com.sungcor.exam.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.sungcor.exam.utils.TimeUtil.changeFormat;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    ServerService serverService;

    @RequestMapping("insert")
//    @Scheduled(cron = "1/5 * * * * ?")
    @Scheduled(fixedDelayString = "${task.data.push.fixed}")
    public Result<?> set24HourStatus(){
        Date date = new Date();
        String keys="server:"+changeFormat(date);
        Map<String,Object> serverStatus = serverService.getStatus2();
        redisUtil.set(keys,serverStatus.toString());
        redisUtil.expire(keys,3600);
        return Result.ok("更新Redis数据成功"+date);
    }
}
