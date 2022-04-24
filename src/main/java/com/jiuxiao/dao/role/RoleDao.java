package com.jiuxiao.dao.role;

import com.jiuxiao.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色信息数据库层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 15:06
 * @since 1.0.0
 */
public interface RoleDao {

    /**
     * 获取角色列表
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
