package com.sungcor.exam.controller;

import com.sungcor.exam.entity.Postdata;
import com.sungcor.exam.service.ServerService;
import com.sungcor.exam.utils.MapUtil;
import com.sungcor.exam.utils.Result;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api("Server数据接口")
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping("getServer")
    @ApiOperation(value = "接受数据并更新数据库")
    public Result<?> getServerAndSwitch(@RequestBody Postdata postdata){
        return Result.ok(serverService.getServer(postdata));
    }

    @GetMapping("/updateOffLine")
//    @Scheduled(fixedDelayString = "${task.data.push.fixed}")
    @XxlJob("updateOffLine")
    @ApiOperation(value = "更新离线次数")
    public ReturnT<String> updateOffLine(String param) {
        XxlJobHelper.log("XXL-JOB: 更新离线次数成功.");
        return new ReturnT(serverService.updateOffLine());
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取全部最新设备列表")
    public Result<?> serverList(){
        return Result.ok(serverService.serverList());
    }


    @GetMapping("/helloworld")
    @XxlJob("MyHello")
    public ReturnT<String> Hello(String param){
//        XxlJobLogger.log("myFirstTsk");
        System.out.println("hello,world");
        return ReturnT.SUCCESS;
    }

//    @GetMapping("/getStatus")
//    @XxlJob("getStatus")
//    @ApiOperation(value = "获取全部设备在线情况")
//    public ReturnT<String> getServerStatus(String param){
//        return new ReturnT(serverService.getStatus());
//    }

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
