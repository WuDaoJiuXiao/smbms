package com.jiuxiao.service.provider;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.dao.bill.BillDao;
import com.jiuxiao.dao.bill.BillDaoImpl;
import com.jiuxiao.dao.provider.ProviderDao;
import com.jiuxiao.dao.provider.ProviderDaoImpl;
import com.jiuxiao.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 供应商业务层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 20:31
 * @since 1.0.0
 */
public class ProviderServiceImpl implements ProviderService {

    private ProviderDao providerDao;
    private BillDao billDao;

    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
        billDao = new BillDaoImpl();
    }

    /**
     * 供应商列表
     *
     * @param proName
     * @param proCode
     * @return
     */
    public List<Provider> getProviderList(String proName, String proCode) {
        Connection connection = null;
        List<Provider> providerList = null;

        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.getProviderList(connection, proName, proCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return providerList;
    }

    /**
     * 增加供应商
     *
     * @param provider
     * @return
     */
    public boolean add(Provider provider) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            if (providerDao.add(connection, provider) > 0) {
                flag = true;
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 删除供应商
     *
     * @param delId
     * @return
     */
    public int deleteProviderById(String delId) {
        Connection connection = null;
        int billCount = -1;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            billCount = billDao.getBillCountByProviderId(connection, delId);
            if (billCount == 0) {
                providerDao.deleteProviderById(connection, delId);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            billCount = -1;
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return billCount;
    }

    /**
     * 修改供应商信息
     *
     * @param provider
     * @return
     */
    public boolean modify(Provider provider) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if (providerDao.modify(connection, provider) > 0) {
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
     * 获取供应商信息
     *
     * @param id
     * @return
     */
    public Provider getProviderById(String id) {
        Provider provider = null;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            provider = providerDao.getProviderById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
            provider = null;
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return provider;
    }

}
