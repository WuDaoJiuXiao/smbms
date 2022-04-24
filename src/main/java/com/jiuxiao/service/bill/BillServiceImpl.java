package com.jiuxiao.service.bill;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.dao.bill.BillDao;
import com.jiuxiao.dao.bill.BillDaoImpl;
import com.jiuxiao.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单业务层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 18:11
 * @since 1.0.0
 */
public class BillServiceImpl implements BillService {

    private BillDao billDao;

    public BillServiceImpl() {
        billDao = new BillDaoImpl();
    }

    /**
     * 增加订单
     *
     * @param bill
     * @return
     */
    public boolean add(Bill bill) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启JDBC事务管理
            if (billDao.add(connection, bill) > 0) {
                flag = true;
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 获取订单列表
     *
     * @param bill
     * @return
     */
    public List<Bill> getBillList(Bill bill) {
        Connection connection = null;
        List<Bill> billList = null;

        try {
            connection = BaseDao.getConnection();
            billList = billDao.getBillList(connection, bill);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return billList;

    }

    /**
     * 删除订单
     *
     * @param delId
     * @return
     */
    public boolean deleteBillById(String delId) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (billDao.deleteBillById(connection, delId) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    public Bill getBillById(String id) {
        Bill bill = null;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            bill = billDao.getBillById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
            bill = null;
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return bill;
    }

    /**
     * 修改订单信息
     *
     * @param bill
     * @return
     */
    public boolean modify(Bill bill) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (billDao.modify(connection, bill) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return flag;
    }
}
