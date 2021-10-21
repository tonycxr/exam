package com.sungcor.exam.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToClass {

    private static final String theURLToFindServer = "http://192.168.0.28/store/openapi/v2/resources/query?apikey=e10adc3949ba59abbe56e057f2gg88dd";

    public static postData setPostDb(){
        conditions conditions = new conditions();
        List<String> list = new ArrayList<>();
        list.add("PCServer");
        list.add("Switch");
        conditions.setField("classCode");
        conditions.setOperator("IN");
        conditions.setValue(list);
        postData postData = new postData();
        postData.setPageSize(200000);
        postData.setNeedCount(true);
        postData.setPageNum(0);
        conditions[] co1 = new conditions[]{conditions};
        postData.setConditions(co1);
        return postData;
    }

    @Test
    public List<Server> getServer() throws Exception{
        //post请求
        HttpMethod method = HttpMethod.POST;
        String s = JSONObject.toJSONString(setPostDb());
        JSONObject json = JSONObject.parseObject(s);
//        System.out.println("发送数据：" + json.toString());
        String res = HttpRestClient(theURLToFindServer, method, json);
        getData getdata = JsontoInterChanger(res);
        dataList[] dataLists = getdata.getDataList();
        List<Server> servers = new ArrayList<>();
        for(dataList datalist:dataLists){
            String url = "http://192.168.0.28/network/v2/openapi/datasets/states/" +
                    "object_available/query?apikey=9cc4871e46094635a19d26557f9bb7f4&object_ids="+datalist.getId()+"&state=&object_type=object.available";
            Server server = new Server(url);
            server.setId(datalist.getId());
            server.setName(datalist.getName());
            server.setIp(datalist.getIp());
            server.setClassCode(datalist.getClassCode());
            servers.add(server);
        }
        for(Server server: servers){
            String res2 = HttpRestClient(server.getUrl(),HttpMethod.GET,json);
//            System.out.println(res2);
            getData getdata2 = JsontoInterChanger(StringUtils.strip(res2,"[]"));
            if(getdata2!=null){
                server.setValue(getdata2.getValue());
            }
            else{
                server.setValue("unknown");
            }
        }
        return servers;
    }

    public static String HttpRestClient(String url, HttpMethod method, JSONObject json) throws IOException {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10 * 1000);
        requestFactory.setReadTimeout(10 * 1000);
        RestTemplate client = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity(json.toString(), headers);
        //  执行HTTP请求
        ResponseEntity response = client.exchange(url, method, requestEntity, String.class);
        return (String) response.getBody();
    }

    public static getData JsontoInterChanger(String result){
        return JSON.parseObject(result, getData.class);
    }
}
