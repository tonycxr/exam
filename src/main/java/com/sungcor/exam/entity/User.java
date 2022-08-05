package com.sungcor.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String userName;
    private String password;
    private String role;

    public User() {

    }
}
