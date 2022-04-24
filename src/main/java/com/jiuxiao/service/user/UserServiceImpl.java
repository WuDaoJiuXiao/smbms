package com.jiuxiao.service.user;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.dao.user.UserDao;
import com.jiuxiao.dao.user.UserDaoImpl;
import com.jiuxiao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户信息业务层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:55
 * @since 1.0.0
 */
public class UserServiceImpl implements UserService {

    //业务层都会使用 Dao 层，所以要引入 Dao 层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    /**
     * 用户登录
     *
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return user;
    }

    /**
     * 根据用户 Id 修改密码
     *
     * @param password
     * @param id
     * @return
     */
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection, id, password) > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 查询用户数量
     *
     * @param username
     * @param userRole
     * @return
     */
    public int getUserCount(String username, int userRole) {
        int count = 0;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, username, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return count;
    }

    /**
     * 根据条件获取用户列表
     *
     * @param queryUserName
     * @param queryUserRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return userList;
    }

    /**
     * 增加用户信息
     *
     * @param user
     * @return
     */
    public boolean add(User user) {
        boolean flag = false;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int updateRows = userDao.add(connection, user);
            connection.commit();
            if (updateRows > 0) {
                flag = true;
                System.out.println("add success!");
            } else {
                System.out.println("add failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 根据 ID 删除用户
     *
     * @param delId
     * @return
     */
    public boolean deleteUserById(Integer delId) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (userDao.deleteUserById(connection, delId) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public boolean modify(User user) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (userDao.modify(connection, user) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 根据 UserCode 查询用户是否存在
     *
     * @param userCode
     * @return
     */
    public User selectUserCodeExist(String userCode) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return user;
    }

    /**
     * 根据 ID 查询用户
     *
     * @param id
     * @return
     */
    public User getUserById(String id) {
        User user = null;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return user;
    }
}
