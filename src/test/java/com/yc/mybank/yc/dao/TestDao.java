package com.yc.mybank.yc.dao;



import com.yc.mybank.bean.Accounts;
import com.yc.mybank.dao.AccountsDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


//指定在单元测试启动的时候创建spring的工厂类对象
@SpringBootTest
//RunWith的value属性指定以spring test的SpringJUnit4ClassRunner作为启动类
//如果不指定启动类，默认启用的junit中的默认启动类
@RunWith(value = SpringJUnit4ClassRunner.class)
public class TestDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountsDao accountsDao;

    @Test
    public void DataSource() throws SQLException {
        Assert.assertNotNull(dataSource);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void OpenAccounts() {
        Accounts a=new Accounts();
        a.setBalance(10.0);
        int accountId=accountsDao.saveAccount(a);
        System.out.println("开户成功，新开户id为："+accountId);
    }


    @Test
    public void testFindAll(){
        List<Accounts> list=this.accountsDao.findAll();
        System.out.println(list);
    }

    @Test
    public void testFindByid() throws SQLException {
        Accounts a = this.accountsDao.findAccount(4);
        System.out.println(a);
    }

}
