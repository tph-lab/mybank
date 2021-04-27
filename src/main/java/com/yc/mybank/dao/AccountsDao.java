package com.yc.mybank.dao;



import com.yc.mybank.bean.Accounts;

import java.util.List;

public interface AccountsDao {

    public int saveAccount(Accounts accounts);

    public Accounts updateAccount(Accounts accounts);

    public Accounts findAccount(int accountid);

    public List<Accounts> findAll();

    public void delete(int accountid);
}
