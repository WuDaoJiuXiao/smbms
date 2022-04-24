package com.jiuxiao.service.user;

import com.jiuxiao.pojo.User;

import java.util.List;

/**
 * 用户登录业务层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:54
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode, String password);

    /**
     * 根据用户 Id 修改密码
     *
     * @param password
     * @param id
     * @return
     */
    public boolean updatePwd(int id, String password);

    /**
     * 查询用户数量
     *
     * @param username
     * @param userRole
     * @return
     */
    public int getUserCount(String username, int userRole);

    /**
     * 根据条件获取用户列表
     *
     * @param queryUserName
     * @param queryUserRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

    /**
     * 增加用户信息
     *
     * @param user
     * @return
     */
    public boolean add(User user);

    /**
     * 根据 ID 删除用户
     *
     * @param delId
     * @return
     */
    public boolean deleteUserById(Integer delId);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public boolean modify(User user);

    /**
     * 根据 UserCode 查询用户是否存在
     *
     * @param userCode
     * @return
     */
    public User selectUserCodeExist(String userCode);

    /**
     * 根据 ID 查询用户
     *
     * @param id
     * @return
     */
    public User getUserById(String id);
}
