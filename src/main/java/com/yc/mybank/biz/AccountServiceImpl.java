package com.yc.mybank.biz;


import com.yc.mybank.bean.Accounts;
import com.yc.mybank.bean.OpRecord;
import com.yc.mybank.bean.OpTypes;
import com.yc.mybank.dao.AccountsDao;
import com.yc.mybank.dao.OpRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


//不需要写@EnableTransactionManagement，因为springboot自动开启事务
@Transactional(propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = false,
        timeout = 100,
        rollbackForClassName = {"RuntimeException"})
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private OpRecordDao opRecordDao;


    //返回添加账户之后的accountid
    @Override
    public Integer openAccount(Accounts account, double money) {
        account.setBalance(money);
        return accountsDao.saveAccount(account);
    }

    @Override
    public Accounts deposite(Accounts account, double money,String optype,String transferid) {

        //调用查询
        Accounts a=this.showBalance(account);

        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(a.getAccountId());
        opRecord.setOpmoney(money);
        opRecord.setOptype(optype);
        opRecord.setOptime(new Timestamp(System.currentTimeMillis()));
        if(transferid==null){
            opRecord.setTransferid(" ");
        }else {
            opRecord.setTransferid(transferid);
        }
        opRecordDao.saveOpRecord(opRecord);

        a.setBalance(a.getBalance()+money);
        accountsDao.updateAccount(a);
        return a;
    }


    @Override
    public Accounts withdraw(Accounts account, double money,String optype,String transferid) {
        //根据账号id查询
        Accounts a = this.showBalance(account);

        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(a.getAccountId());
        opRecord.setOpmoney(money);
        opRecord.setOptype(optype);
        //System.currentTimeMillis()为long类型
        //需要将long类型转为时间戳
        opRecord.setOptime(new Timestamp(System.currentTimeMillis()));
        if (transferid == null) {
            opRecord.setTransferid(" ");
        } else {
            opRecord.setTransferid(transferid);
        }
        opRecordDao.saveOpRecord(opRecord);

        a.setBalance(a.getBalance() - money);
        accountsDao.updateAccount(a);
        return a;
    }

    @Override
    public Accounts transfer(Accounts inAccount, Accounts outAccount, double money) {
        String uid = UUID.randomUUID().toString();   //转账流水
        this.deposite(inAccount, money, OpTypes.deposite.getName(), uid);
        Accounts newAccounts = this.withdraw(outAccount, money, OpTypes.withdraw.getName(), uid);
        return newAccounts;
    }

    //只读事务可以提高性能
    @Override
    @Transactional(readOnly = true)
    public Accounts showBalance(Accounts account) {
        return accountsDao.findAccount(account.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpRecord> findById(Accounts account) {
        return opRecordDao.findByAccountid(account.getAccountId());
    }
}
