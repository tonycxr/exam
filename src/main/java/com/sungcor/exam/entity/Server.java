package com.sungcor.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
    private int offLineCount;
    private Date create_time;

    public Server(String url) {
        this.url = url;
    }

    public Server(String id,int offLineCount){
        this.id = id;
        this.offLineCount = offLineCount;
    }
}
