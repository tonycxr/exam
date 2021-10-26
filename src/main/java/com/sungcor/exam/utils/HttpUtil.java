package com.sungcor.exam.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sungcor.exam.entity.*;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class HttpUtil {

    public String HttpRestClient(String url, HttpMethod method, JSONObject json) {
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

    public Getdata JsonToInterChanger(String result){
        return JSON.parseObject(result, Getdata.class);
    }
}
