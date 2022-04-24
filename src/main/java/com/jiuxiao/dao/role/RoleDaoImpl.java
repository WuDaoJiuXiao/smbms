package com.jiuxiao.dao.role;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息数据库层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 15:06
 * @since 1.0.0
 */
public class RoleDaoImpl implements RoleDao {
    /**
     * 获取角色列表
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Role> getRoleList(Connection connection) throws SQLException {
        List<Role> roleList = new ArrayList<Role>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] param = {};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, param);
            while (resultSet.next()) {
                Role _role = new Role();
                _role.setId(resultSet.getInt("id"));
                _role.setRoleCode(resultSet.getString("roleCode"));
                _role.setRoleName(resultSet.getString("roleName"));
                roleList.add(_role);
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return roleList;
    }

}
