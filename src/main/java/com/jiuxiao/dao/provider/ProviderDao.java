package com.jiuxiao.dao.provider;

import com.jiuxiao.pojo.Provider;

import java.sql.Connection;
import java.util.List;

/**
 * 供应商数据库层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 17:55
 * @since 1.0.0
 */
public interface ProviderDao {
    /**
     * 获取供应商列表
     *
     * @param connection
     * @param proName
     * @return
     * @throws Exception
     */
    public List<Provider> getProviderList(Connection connection, String proName, String proCode) throws Exception;

    /**
     * 增加供应商
     *
     * @param connection
     * @param provider
     * @return
     * @throws Exception
     */
    public int add(Connection connection, Provider provider) throws Exception;

    /**
     * 删除供应商
     *
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteProviderById(Connection connection, String delId) throws Exception;

    /**
     * 修改供应商信息
     *
     * @param connection
     * @param provider
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, Provider provider) throws Exception;

    /**
     * 获取供应商信息
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public Provider getProviderById(Connection connection, String id) throws Exception;
}
