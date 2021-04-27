package com.yc.mybank.dao;



import com.yc.mybank.bean.OpRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OpRecordDaoImpl implements OpRecordDao {

    //需要导入spring-jdbc，整合了jdbc和spring
    //需要根据datasource创建
    private JdbcTemplate jdbcTemplate;


    @Autowired//通过dataSource来创建jdbctemplate
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }


    @Override
    public void saveOpRecord(OpRecord opRecord) {
        String sql="insert into oprecord(accountid,opmoney,optime,optype,transferid) values(?,?,?,?,?) ";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setInt(1,opRecord.getAccountid());
            pstmt.setDouble(2,opRecord.getOpmoney());
            pstmt.setTimestamp(3,opRecord.getOptime());
            pstmt.setString(4,opRecord.getOptype());
            pstmt.setString(5,opRecord.getTransferid());
            return pstmt;
        });


    }

    @Override
    public List<OpRecord> findAll() {
        String sql="select *from oprecord";
        List<OpRecord> list=this.jdbcTemplate.query(sql,(resultSet,rowNum)->{
            OpRecord a=new OpRecord();
            a.setId(resultSet.getInt("id"));
            a.setAccountid(resultSet.getInt("accountid"));
            a.setOpmoney(resultSet.getDouble("opmoney"));
            a.setOptime(resultSet.getTimestamp("optime"));
            a.setOptype(resultSet.getString("optype"));
            a.setTransferid(resultSet.getString("transferid"));
            return a;
        });
        return list;
    }

    @Override
    public List<OpRecord> findByAccountid(int accountid) {
        String sql="select *from oprecord where accountid=?";
        List<OpRecord> list=this.jdbcTemplate.query(sql,(resultSet,rowNum)->{
            OpRecord a=new OpRecord();
            a.setId(resultSet.getInt("id"));
            a.setAccountid(resultSet.getInt("accountid"));
            a.setOpmoney(resultSet.getDouble("opmoney"));
            a.setOptime(resultSet.getTimestamp("optime"));
            a.setOptype(resultSet.getString("optype"));
            a.setTransferid(resultSet.getString("transferid"));
            return a;
        },accountid);
        return list;
    }
}
