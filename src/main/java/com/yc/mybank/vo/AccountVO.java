package com.yc.mybank.vo;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.io.Serializable;

@Data
public class AccountVO implements Serializable {

    private Integer accountId;
    private Double money;
    private Integer inAccountId;
}
