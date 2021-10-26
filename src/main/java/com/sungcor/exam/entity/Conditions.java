package com.sungcor.exam.entity;

import lombok.Data;

import java.util.List;

@Data
public class Conditions {
    private String field;
    private String operator;
    private List<String> value;
}
