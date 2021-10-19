package com.sungcor.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Server implements Serializable{
    private String id;
    private String name;
    private String ip;
    private String classCode;
    private String value;
    private String url;

    public Server(String url) {
        this.url = url;
    }
}
