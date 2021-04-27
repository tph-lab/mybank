package com.yc.mybank.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OpRecord {
    private Integer id;
    private Integer accountid;
    private Double opmoney;
    private Timestamp optime;//TODO:    dateTime
    private String optype;//TODO:       enum
    private String transferid;
}
