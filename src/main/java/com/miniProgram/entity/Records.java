package com.miniProgram.entity;

import lombok.Data;

import java.time.LocalTime;


@Data
public class Records {

    private String id;

    private Integer count;

    private LocalTime nowTime;

    private Integer level;



}
