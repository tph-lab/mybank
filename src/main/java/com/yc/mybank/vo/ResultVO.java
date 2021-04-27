package com.yc.mybank.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {
   private Integer code;
   private T data;
   private String msg;

}
