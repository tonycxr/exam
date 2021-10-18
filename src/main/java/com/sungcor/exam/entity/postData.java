package com.sungcor.exam.entity;

import lombok.Data;

import java.awt.print.Book;

@Data
public class postData {
    private Integer pageSize;
    private Integer pageNum;
    private Boolean needCount;
    private conditions[] conditions;
}
