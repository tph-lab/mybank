package com.yc.mybank.dao;


import com.yc.mybank.bean.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AccountsDaoImpl implements AccountsDao{

    //需要导入spring-jdbc，整合了jdbc和spring
    //需要根据datasource创建
   private JdbcTemplate jdbcTemplate;


   @Autowired//通过dataSource来创建jdbctemplate
   public void setDataSource(DataSource dataSource){
       this.jdbcTemplate=new JdbcTemplate(dataSource);
   }



    @Override
    public int saveAccount(Accounts account) {
        String sql="insert into accounts(balance) values(?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();//生成键的保存器
        //添加数据，得到生成键的保存器
        //connection由spring调用的时候注入
        this.jdbcTemplate.update(connection -> {//lambda表达式   指定自动增加的键
            PreparedStatement ps=connection.prepareStatement(sql,new String[]{"accountid"});
            //index从0开始
            //根据类型设置参数
            ps.setDouble(1,account.getBalance());
            return ps;
        },keyHolder);

//        //方案一：用匿名内部类书写
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps=connection.prepareStatement(sql,new String[]{"accountid"});
//                ps.setDouble(1,account.getBalance());
//                return ps;
//            }
//        },keyHolder);


        return  keyHolder.getKey().intValue();//返回自动生成的键
    }

    @Override
    public Accounts updateAccount(Accounts account) {
       //根据条件采取更新操作
        String sql = "update accounts set balance = ? where accountid = ?";
        this.jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
        return account;
    }

    @Override
    public Accounts findAccount(int accountid) {
        String sql="select *from accounts where accountid=?";
        //根据条件查询封装对象
        return this.jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    //自动循环结果集，resultset是一行数据
                    Accounts a=new Accounts();
                    a.setAccountId(resultSet.getInt("accountid"));
                    a.setBalance(resultSet.getDouble("balance"));
                    return a;
        },
        accountid);
    }

    @Override
    public List<Accounts> findAll() {
       String sql="select *from accounts";
        //查询所有
        List<Accounts> list=this.jdbcTemplate.query(
                sql,
                (resultset,rowNum) -> {
                    System.out.println("当前取的是第："+rowNum+"行的数据");
                    Accounts a=new Accounts();
                    //resultset为jvm自动循环的
                    a.setAccountId(resultset.getInt("accountid"));
                    a.setBalance(resultset.getDouble("balance"));
                    return a;
        });





//       List<Accounts> list=this.jdbcTemplate.query(sql, new RowMapper<Accounts>() {
//           @Override
//           public Accounts mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//               System.out.println("当前取的是第："+rowNum+"行的数据");
//               Accounts a=new Accounts();
//               a.setAccoutId(resultSet.getInt("accountid"));
//               a.setBalance(resultSet.getDouble("balance"));
//               return a;
//           }
//       });
       return list;
    }

    @Override
    public void delete(int accountid) {
        String sql = "delete from accounts where accountid = ?";
        this.jdbcTemplate.update(sql, accountid);
    }


}


