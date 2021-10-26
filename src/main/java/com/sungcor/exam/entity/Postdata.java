package com.sungcor.exam.entity;

import lombok.Data;

@Data
public class Postdata {
    private Integer pageSize;
    private Integer pageNum;
    private Boolean needCount;
    private Conditions[] conditions;
}
