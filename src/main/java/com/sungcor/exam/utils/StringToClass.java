package com.sungcor.exam.utils;

import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.PostData;
import com.sungcor.exam.entity.conditions;
import com.sungcor.exam.entity.getData;
import com.sungcor.exam.entity.dataList;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToClass {

    public static PostData setPostDb(){
        conditions conditions = new conditions();
        List<String> list = new ArrayList<>();
        list.add("PCServer");
        list.add("Switch");
        conditions.setField("classCode");
        conditions.setOperator("IN");
        conditions.setValue(list);
        PostData postData = new PostData();
        postData.setPageSize(2);
        postData.setNeedCount(true);
        postData.setPageNum(0);
        conditions[] co1 = new conditions[]{conditions};
        postData.setConditions(co1);
        return postData;
    }


    public static void main(String[] args) throws IOException {
        StringToClass jsontoObject = new StringToClass();
        utilsforjson receiveJson = new utilsforjson();
        String url = "http://192.168.0.28/store/openapi/v2/resources/query?apikey=e10adc3949ba59abbe56e057f2gg88dd";
        //post请求
        HttpMethod method = HttpMethod.POST;
        String s = JSONObject.toJSONString(setPostDb());
        System.out.println(s);
        JSONObject json = JSONObject.parseObject(s);
        System.out.println("发送数据：" + json.toString());
//        System.out.println(json);
//        发送http请求并返回结果
        String result = null;
        try {
            result = receiveJson.HttpRestClient(url, method, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(result);
        utilsforjson utilfj = new utilsforjson();
        getData getdata = utilfj.JsontoInterChanger(result);
        dataList[] dataLists = getdata.getDatalist();
        System.out.println(Arrays.asList(dataLists));
        List<String> listForId = new ArrayList<>();
        for(dataList dataList:dataLists){
            listForId.add(dataList.getId());
        }
        System.out.println(listForId);
    }

}
