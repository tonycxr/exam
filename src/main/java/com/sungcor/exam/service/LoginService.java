package com.sungcor.exam.service;

import com.sungcor.exam.entity.User;
import com.sungcor.exam.mapping.LoginMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public String addUserByAdmin(@RequestBody User user) {
        List<User> allUsers = loginMapper.getAllUser();
        for(User userInRepository:allUsers){
            if(user.getUserName().equals(userInRepository.getUserName())){
                return "添加失败，已存在用户名";
            }
        }
        loginMapper.insertUser(user);
        return "添加用户成功";
    }

    public String deleteUserByName(String name){
        if(name.equals("Admin")){
            return "不能删除最高管理员账户！";
        }
        List<User> allUsers = loginMapper.getAllUser();
        for(User userInRepository:allUsers){
            if(userInRepository.getUserName().equals(name)){
                loginMapper.deleteUser(name);
                return "删除用户成功";
            }
        }
        return "不存在该账号";
    }
}
