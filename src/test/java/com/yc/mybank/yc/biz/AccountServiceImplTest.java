package com.yc.mybank.yc.biz;

import com.yc.mybank.biz.AccountService;

import com.yc.mybank.bean.Accounts;
import com.yc.mybank.bean.OpTypes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//指定在单元测试启动的时候创建spring的工厂类对象
@SpringBootTest
//RunWith的value属性指定以spring test的SpringJUnit4ClassRunner作为启动类
//如果不指定启动类，默认启用的junit中的默认启动类
//@RunWith(value = SpringJUnit4ClassRunner.class)   一样的
@RunWith(value = SpringRunner.class)
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;


    @Transactional //在junit中通过使用  @Transactional  在测试完后，用恢复现场.
    @Test
    public void openAccount() {
        Integer accountid=accountService.openAccount(new Accounts(),1);
        System.out.println(accountid);
        Assert.assertNotNull(accountid);
    }

    @Test
    public void deposite() {
        Accounts a=new Accounts();
        a.setAccountId(6);
        Accounts aa=accountService.deposite(a,200, OpTypes.deposite.getName(),null);
    }

    @Test
    public void withdraw() {
        Accounts a=new Accounts();
        a.setAccountId(6);
        Accounts aa=accountService.withdraw(a,999,OpTypes.withdraw.getName(),null);
        System.out.println(aa);
    }

    @Test
    public void transfer() {
        Accounts out=new Accounts();
        out.setAccountId(6);

        Accounts inA=new Accounts();
        inA.setAccountId(7);

        this.accountService.transfer(inA,out,5);
    }

    @Test
    public void showBalance() {
        Accounts a=new Accounts();
        a.setAccountId(6);
        a=this.accountService.showBalance(a);
        System.out.println(a);
    }

    @Test
    public void findById() {
    }
}