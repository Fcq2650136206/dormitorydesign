package com.southwind.dao.impl;

import com.southwind.dao.SystemAdminDao;
import com.southwind.entity.SystemAdmin;
import com.southwind.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdminDaoImpl implements SystemAdminDao {
    @Override
    public SystemAdmin findByUsername(String username) {
        //获取连接
        Connection connection = JDBCUtil.getConnection();
        //准备执行sql
        String sql="select * from system_admin where username='"+username+"'";
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            statement=connection.prepareStatement(sql);
            resultSet= statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String name = resultSet.getString(4);
                String telephone = resultSet.getString(5);
                SystemAdmin systemAdmin = new SystemAdmin(id,username,password,name,telephone);
                return systemAdmin;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.release(connection,statement,resultSet);
        }
        return null;
    }
}
