package com.southwind.util;

import java.sql.*;

public class JDBCUtil {
    private static String driverName="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/dormitory?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static String user="root";
    private static String password="123456";
    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //获取连接
    public static Connection getConnection(){
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    //关闭连接
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                connection.close();
            }
            if(connection!=null){
                connection.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
