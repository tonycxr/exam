package com.sungcor.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Server {
    private dataList datalist;
    private String value;
    private String url;

    public Server(dataList datalist,String url) {
        this.datalist = datalist;
        this.url = url;
    }
}
