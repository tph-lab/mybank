package com.yc.mybank.dao;


import com.yc.mybank.bean.OpRecord;

import java.util.List;

public interface OpRecordDao {

    public void saveOpRecord(OpRecord opRecord);

    public List<OpRecord> findAll();

    public List<OpRecord> findByAccountid(int accountid);
}
