package com.southwind.dao.impl;

import com.southwind.dao.MoveoutDao;
import com.southwind.entity.Moveout;
import com.southwind.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveoutDaoImpl implements MoveoutDao {
    @Override
    public Integer save(Moveout moveout) {
        //获取连接
        Connection connection = JDBCUtil.getConnection();
        //准备执行sql注意空
        String sql="insert into moveout(student_id,dormitory_id,reason,create_date) value(?,?,?,?)";
        PreparedStatement statement=null;
        Integer result=null;
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,moveout.getStudentId());
            statement.setInt(2,moveout.getDormitoryId());
            statement.setString(3,moveout.getReason());
            statement.setString(4,moveout.getCreateDate());
            result= statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection,statement,null);
        }
        return result;
    }

    @Override
    public List<Moveout> list() {
        //获取连接
        Connection connection = JDBCUtil.getConnection();
        //准备执行sql
        String sql="select m.id,s.name,d.name,m.reason,m.create_date from student s,dormitory d,moveout m where m.student_id=s.id and m.dormitory_id=d.id";
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Moveout> list = new ArrayList<>();
        try {
            statement=connection.prepareStatement(sql);
            resultSet= statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String studentName = resultSet.getString(2);
                String dormitoryName = resultSet.getString(3);
                String reason=resultSet.getString(4);
                String createDate = resultSet.getString(5);
                list.add(new Moveout(id,studentName,dormitoryName,reason,createDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection,statement,resultSet);
        }
        return list;
    }

    @Override
    public List<Moveout> search(String key, String value) {
        //获取连接
        Connection connection = JDBCUtil.getConnection();
        //准备执行sql
        String sql="select m.id,s.name,d.name,m.reason,m.create_date from student s,dormitory d,moveout m where m.student_id=s.id and m.dormitory_id=d.id";
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Moveout> list = new ArrayList<>();
        String keyStatement=null;
        try {
            if(key.equals("studentName")){
                keyStatement=" and s.name";
            }
            if(key.equals("dormitoryName")){
                keyStatement=" and d.name";
            }
            sql=sql+keyStatement+" like '%"+value+"%'";
            statement=connection.prepareStatement(sql);
            resultSet= statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String studentName = resultSet.getString(2);
                String dormitoryName = resultSet.getString(3);
                String reason=resultSet.getString(4);
                String createDate = resultSet.getString(5);
                list.add(new Moveout(id,studentName,dormitoryName,reason,createDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection,statement,resultSet);
        }
        return list;
    }
}
