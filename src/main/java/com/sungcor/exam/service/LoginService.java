package com.sungcor.exam.service;

import com.sungcor.exam.entity.User;
import com.sungcor.exam.mapping.LoginMapper;
import org.springframework.stereotype.Service;

import java.security.Permissions;
import java.util.*;

@Service
public class LoginService {

    private final LoginMapper loginMapper;

    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public User getMapByName(String userName) {
        List<User> allUsers = loginMapper.getAllUser();
        Map<String, User> map = new HashMap<>();
        for(User user:allUsers){
            map.put(user.getUserName(),user);
        }
        return map.get(userName);
    }
}
