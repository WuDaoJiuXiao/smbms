package com.jiuxiao.dao.user;

import com.jiuxiao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据库层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 16:12
 * @since 1.0.0
 */
public interface UserDao {

    /**
     * 得到用户登录的信息
     *
     * @param connection
     * @param userCode
     * @return
     * @throws SQLException
     */
    public User getLoginUser(Connection connection, String userCode) throws SQLException;

    /**
     * 修改用户密码
     *
     * @param connection
     * @param id
     * @param password
     * @return
     */
    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    /**
     * 根据用户名或者角色，获取用户总数
     *
     * @param connection
     * @param username
     * @param userRole
     * @return
     * @throws SQLException
     */
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException;

    /**
     * 根据条件获取用户列表
     *
     * @param connection
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws SQLException
     */
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException;

    /**
     * 增加用户信息
     *
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int add(Connection connection, User user) throws Exception;

    /**
     * 删除用户
     *
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteUserById(Connection connection, Integer delId) throws Exception;

    /**
     * 修改用户信息
     *
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, User user) throws Exception;

    /**
     * 获取用户信息
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(Connection connection, String id) throws Exception;
}

