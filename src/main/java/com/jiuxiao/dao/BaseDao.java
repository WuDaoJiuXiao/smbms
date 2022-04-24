package com.jiuxiao.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库基础公共类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/20 17:01
 * @since 1.0.0
 */
public class BaseDao {

    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    //类加载的时候就初始化
    static {
        Properties properties = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //查询公共方法
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet,
                                    String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        //setObject 占位符从 1 开始，但是数组下标从 0 开始
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //增删改公共方法
    public static int execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        //预编译的 sql，在后面不用加，直接执行就行
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    //关闭连接，释放资源
    public static boolean closeResource(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;   //GC回收
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
