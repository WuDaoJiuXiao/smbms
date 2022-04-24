package com.jiuxiao.service.bill;

import com.jiuxiao.pojo.Bill;

import java.util.List;

/**
 * 订单业务层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 18:10
 * @since 1.0.0
 */
public interface BillService {
    /**
     * 增加订单
     *
     * @param bill
     * @return
     */
    public boolean add(Bill bill);

    /**
     * 获取订单列表
     *
     * @param bill
     * @return
     */
    public List<Bill> getBillList(Bill bill);

    /**
     * 删除订单
     *
     * @param delId
     * @return
     */
    public boolean deleteBillById(String delId);

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    public Bill getBillById(String id);

    /**
     * 修改订单信息
     *
     * @param bill
     * @return
     */
    public boolean modify(Bill bill);
}
