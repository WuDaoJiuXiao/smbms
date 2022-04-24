package com.jiuxiao.service.role;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.dao.role.RoleDao;
import com.jiuxiao.dao.role.RoleDaoImpl;
import com.jiuxiao.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色信息业务层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 15:11
 * @since 1.0.0
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return roleList;
    }
}
