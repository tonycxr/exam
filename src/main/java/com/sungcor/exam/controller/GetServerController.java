package com.sungcor.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.conditions;
import com.sungcor.exam.entity.dataList;
import com.sungcor.exam.utils.utilsforjson;
import com.sungcor.exam.utils.StringToClass;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GetServerController {

    @GetMapping("http://192.168.0.28/network/v2/openapi/datasets/states/object_available/query?apikey=9cc4871e46094635a19d26557f9bb7f4&object_ids"+""+"&state=&object_type=object.available")
    public String getEquipment(){
        String str = "";
        System.out.println(str);
        return str;
    }

}
