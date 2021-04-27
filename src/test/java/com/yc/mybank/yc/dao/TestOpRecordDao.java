package com.yc.mybank.yc.dao;


import com.yc.mybank.bean.OpRecord;
import com.yc.mybank.bean.OpTypes;
import com.yc.mybank.dao.OpRecordDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class TestOpRecordDao {

    @Autowired
    private OpRecordDao opRecordDao;

    @Test
    public void testSave(){
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(1);
        opRecord.setOpmoney(1.0);
        opRecord.setOptype(OpTypes.deposite.getName());
        opRecord.setOptime(new Timestamp(new Date().getTime()));
        opRecord.setTransferid(" ");
        System.out.println(opRecord+",,,,,,,,,,,,,,,");
        opRecordDao.saveOpRecord(opRecord);
    }

    @Test
    public void testAll(){
        List<OpRecord> list=opRecordDao.findAll();
        //断言查出来的日志长度不会为0
        Assert.assertNotEquals(0,list.size());
    }


    @Test
    public void testByAccountId(){
        List<OpRecord> list=opRecordDao.findByAccountid(1);
        Assert.assertNotEquals(0,list.size());
    }

}
