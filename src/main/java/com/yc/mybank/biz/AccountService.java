package com.yc.mybank.biz;


import com.yc.mybank.bean.Accounts;
import com.yc.mybank.bean.OpRecord;

import java.util.List;

public interface AccountService {

    public Integer openAccount(Accounts accounts, double money);

    public Accounts deposite(Accounts accounts, double money,String optype,String transferid);

    public Accounts withdraw(Accounts accounts,double money,String optype,String transferid);

    public Accounts transfer(Accounts inAccount,Accounts outAccounts,double money);

    public Accounts showBalance(Accounts accounts);

    public List<OpRecord> findById(Accounts accounts);

}
