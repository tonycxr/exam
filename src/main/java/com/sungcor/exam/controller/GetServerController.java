package com.sungcor.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.conditions;
import com.sungcor.exam.entity.dataList;
import com.sungcor.exam.utils.utilsforjson;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class GetServerController {

    @ResponseBody
    @Scheduled(cron = "0 0/30 * * * ?")
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String getEquipment(){
        System.out.println("Hello"+new Date());
        return "hello";
    }

}
