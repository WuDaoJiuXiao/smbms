package com.jiuxiao.dao.bill;

import com.jiuxiao.pojo.Bill;

import java.sql.Connection;
import java.util.List;

/**
 * 订单数据库层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 17:36
 * @since 1.0.0
 */
public interface BillDao {

    /**
     * 增加订单
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public int add(Connection connection, Bill bill) throws Exception;

    /**
     * 获取供应商列表
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public List<Bill> getBillList(Connection connection, Bill bill) throws Exception;

    /**
     * 通过 ID 删除订单
     *
     * @param connection
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteBillById(Connection connection, String delId) throws Exception;

    /**
     * 通过 ID 获取订单
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public Bill getBillById(Connection connection, String id) throws Exception;

    /**
     * 修改订单信息
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, Bill bill) throws Exception;

    /**
     * 根据供应商 ID 查询订单数量
     *
     * @param connection
     * @param providerId
     * @return
     * @throws Exception
     */
    public int getBillCountByProviderId(Connection connection, String providerId) throws Exception;
}
