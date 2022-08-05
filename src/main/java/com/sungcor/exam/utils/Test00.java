package com.sungcor.exam.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sungcor.exam.entity.User;
import org.springframework.util.Assert;

import java.util.Objects.*;

import java.util.Map;
import java.util.Optional;

public class Test00 {
    public static void main(String[] args) {
        User user = new User();
        user.setId(15);
        user.setUserName("tony");
        user.setUserName(null);
        user.setPassword("123444");
        user.setRole("user");
//        user = null;
//        Optional.ofNullable(user).ifPresent(u->{
//            user.setPassword("122212");
//        });
        String s = JSON.toJSONString(user);
//        Assert.notNull(user.getUserName(),"名称不能为空");
//        Map<String,User> map = JSON.parseObject(s,new TypeReference<Map<String,User>>(){});
//        System.out.println(map);
        User user2 = JSON.parseObject(s,User.class);
        System.out.println(user2);
        System.out.println(s);

    }


}
