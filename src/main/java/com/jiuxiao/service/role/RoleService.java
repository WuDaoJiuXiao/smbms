package com.jiuxiao.service.role;

import com.jiuxiao.pojo.Role;

import java.util.List;

/**
 * 角色信息业务层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 15:10
 * @since 1.0.0
 */
public interface RoleService {

    /**
     * 获取角色列表
     *
     * @return
     */
    public List<Role> getRoleList();
}
