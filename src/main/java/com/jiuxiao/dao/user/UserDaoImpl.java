package com.jiuxiao.dao.user;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据库层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:26
 * @since 1.0.0
 */
public class UserDaoImpl implements UserDao {

    /**
     * 得到用户登录的信息
     *
     * @param connection
     * @param userCode
     * @return
     * @throws SQLException
     */
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        User user = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            Object[] params = {userCode};
            String sql = "select * from smbms_user where userCode = ?";

            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return user;
    }

    /**
     * 修改用户密码
     *
     * @param connection
     * @param id
     * @param password
     * @return
     */
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        int execute = 0;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            Object[] params = {password, id};
            String sql = "update smbms_user set userPassword = ? where id = ?";
            execute = BaseDao.execute(connection, preparedStatement, sql, params);
        }
        return execute;
    }

    /**
     * 根据用户名或者角色，获取用户总数(难点)
     *
     * @param connection
     * @param username
     * @param userRole
     * @return
     * @throws SQLException
     */
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        ArrayList<Object> list = new ArrayList<Object>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole = r.id");
            //按照用户名查询
            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and u.userName like ?");
                list.add("%" + username + "%");
            }
            //按照角色查询
            if (userRole > 0) {
                sql.append(" and u.userRole like ?");
                list.add(userRole);
            }

            Object[] param = list.toArray();
            System.out.println("UserDao->getUserCount : " + sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), param);

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return count;
    }

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
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<User>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*, r.roleName as userRoleName from smbms_user u, smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }

            //使用 limit 分页
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] param = list.toArray();
            System.out.println("UserDaoImpl -> getUserList : " + sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), param);
            while (resultSet.next()) {
                User _user = new User();
                _user.setId(resultSet.getInt("id"));
                _user.setUserCode(resultSet.getString("userCode"));
                _user.setUserName(resultSet.getString("userName"));
                _user.setGender(resultSet.getInt("gender"));
                _user.setBirthday(resultSet.getDate("birthday"));
                _user.setPhone(resultSet.getString("phone"));
                _user.setUserRole(resultSet.getInt("userRole"));
                _user.setUserRoleName(resultSet.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return userList;
    }

    /**
     * 增加用户信息
     *
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int add(Connection connection, User user) throws Exception {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "insert into smbms_user (userCode,userName,userPassword," +
                    "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(),
                    user.getUserRole(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getCreationDate(), user.getCreatedBy()};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }

    /**
     * 删除用户
     *
     * @param connection
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteUserById(Connection connection, Integer delId) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;

        if (connection != null) {
            String sql = "delete from smbms_user where id = ?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 修改用户信息
     *
     * @param connection
     * @param user
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, User user) throws Exception {
        int flag = 0;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            String sql = "update smbms_user set userName=?,gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getUserRole(), user.getModifyBy(),
                    user.getModifyDate(), user.getId()};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 获取用户信息
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(Connection connection, String id) throws Exception {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return user;
    }
}
