package com.sungcor.exam.controller;

import com.sungcor.exam.entity.Postdata;
import com.sungcor.exam.entity.Server;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.MapUtil;
import com.sungcor.exam.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Api("Server数据接口")
public class ServerController {
    @Autowired
    private ServerService serverService;

    @PostMapping("getServer")
//    @Scheduled(cron = "0 0/30 * * * ?")
    @ApiOperation(value = "接受数据并更新数据库")
    public Result<?> getServerAndSwitch(@RequestBody Postdata postdata){
        return Result.ok(serverService.getServer(postdata));
    }

//    @Scheduled(cron = "1/5 * * * * ?")
//    @Scheduled(cron = "0 0/30 * * * ?")
//    @GetMapping("/insert")
//    @Scheduled(cron = "0 0/30 * * * ?")
//    @ApiOperation(value = "新增进入历史记录")
//    public Result<?> insertToDatabase() {
//        return Result.ok(serverService.insertEntity());
//    }

    @GetMapping("/updateOffLine")
    @Scheduled(fixedDelayString = "${task.data.push.fixed}")
    @ApiOperation(value = "更新离线次数")
    public Result<?> updateOffLine() {
        return Result.ok(serverService.updateOffLine());
    }

//    @Scheduled(cron = "0 0/30 * * * ?")
//    @GetMapping("/update")
//    @ApiOperation(value = "更新最新数据")
//    public Result<?> updateData(){
//        return Result.ok(serverService.updateEntity());
//    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取全部最新设备列表")
    public Result<?> serverList(){
        return Result.ok(serverService.serverList());
    }

    @GetMapping("/getStatus")
    @ApiOperation(value = "获取全部设备在线情况")
    public Result<?> getServerStatus2(){
        return Result.ok(serverService.getStatus2());
    }

    @GetMapping("/FuzzyQuery/name/{name}")
    @ApiOperation(value = "根据设备名称模糊查询")
    public Result<?> getServerFuzzyName(@PathVariable String name){
        return Result.ok(serverService.getServerByName(name));
    }

    @GetMapping("/FuzzyQuery/ip/{ip}")
    @ApiOperation(value = "根据设备ip模糊查询")
    public Result<?> getServerFuzzyIp(@PathVariable String ip){
        return Result.ok(serverService.getServerByIp(ip));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除最近的设备数据")
    public Result<?> deleteAll(){
        return Result.ok(serverService.deleteAll());
    }

    @GetMapping("/getOffLineCount")
    @ApiOperation(value = "从高到低展示各个设备的离线次数")
    public Result<?> getOffLineCount(){
        Map<String,Integer> serverMap = MapUtil.sortByValueDescending(serverService.serverListOff());
        return Result.ok(serverMap);
    }
}
