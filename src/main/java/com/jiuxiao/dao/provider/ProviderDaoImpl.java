package com.jiuxiao.dao.provider;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.pojo.Provider;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商数据库层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 17:57
 * @since 1.0.0
 */
public class ProviderDaoImpl implements ProviderDao {

    /**
     * 获取供应商列表
     *
     * @param connection
     * @param proName
     * @param proCode
     * @return
     * @throws Exception
     */
    public List<Provider> getProviderList(Connection connection, String proName, String proCode) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Provider> providerList = new ArrayList<Provider>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_provider where 1=1 ");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(proName)) {
                sql.append(" and proName like ?");
                list.add("%" + proName + "%");
            }
            if (!StringUtils.isNullOrEmpty(proCode)) {
                sql.append(" and proCode like ?");
                list.add("%" + proCode + "%");
            }

            Object[] params = list.toArray();
            System.out.println("sql--->" + sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            while (resultSet.next()) {
                Provider _provider = new Provider();
                _provider.setId(resultSet.getInt("id"));
                _provider.setProCode(resultSet.getString("proCode"));
                _provider.setProName(resultSet.getString("proName"));
                _provider.setProDesc(resultSet.getString("proDesc"));
                _provider.setProContact(resultSet.getString("proContact"));
                _provider.setProPhone(resultSet.getString("proPhone"));
                _provider.setProAddress(resultSet.getString("proAddress"));
                _provider.setProFax(resultSet.getString("proFax"));
                _provider.setCreationDate(resultSet.getTimestamp("creationDate"));
                providerList.add(_provider);
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return providerList;
    }

    /**
     * 增加供应商
     *
     * @param connection
     * @param provider
     * @return
     * @throws Exception
     */
    public int add(Connection connection, Provider provider) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;

        if (connection != null) {
            String sql = "insert into smbms_provider (proCode,proName,proDesc," +
                    "proContact,proPhone,proAddress,proFax,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(), provider.getProName(), provider.getProDesc(),
                    provider.getProContact(), provider.getProPhone(), provider.getProAddress(),
                    provider.getProFax(), provider.getCreatedBy(), provider.getCreationDate()};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 删除供应商
     *
     * @param connection
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteProviderById(Connection connection, String delId) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;

        if (connection != null) {
            String sql = "delete from smbms_provider where id = ?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 修改供应商信息
     *
     * @param connection
     * @param provider
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, Provider provider) throws Exception {
        int flag = 0;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            String sql = "update smbms_provider set proName=?,proDesc=?,proContact=?," +
                    "proPhone=?,proAddress=?,proFax=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {provider.getProName(), provider.getProDesc(), provider.getProContact(), provider.getProPhone(), provider.getProAddress(),
                    provider.getProFax(), provider.getModifyBy(), provider.getModifyDate(), provider.getId()};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 获取供应商信息
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public Provider getProviderById(Connection connection, String id) throws Exception {
        Provider provider = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (connection != null) {
            String sql = "select * from smbms_provider where id=?";
            Object[] params = {id};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getTimestamp("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return provider;
    }
}
