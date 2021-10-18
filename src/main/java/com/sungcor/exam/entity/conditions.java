package com.sungcor.exam.entity;

import lombok.Data;

import java.util.List;

@Data
public class conditions {
    private String field;
    private String operator;
    private List<String> value;
}
