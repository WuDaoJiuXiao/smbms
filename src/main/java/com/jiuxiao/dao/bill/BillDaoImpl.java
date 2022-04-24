package com.jiuxiao.dao.bill;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.pojo.Bill;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单数据库层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 17:39
 * @since 1.0.0
 */
public class BillDaoImpl implements BillDao{

    /**
     * 增加订单
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public int add(Connection connection, Bill bill) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;
        if (connection != null) {
            String sql = "insert into smbms_bill (billCode,productName,productDesc," +
                    "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(),
                    bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getCreatedBy(), bill.getCreationDate()};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 获取供应商列表
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public List<Bill> getBillList(Connection connection, Bill bill) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bill> billList = new ArrayList<Bill>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id");
            List<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(bill.getProductName())) {
                sql.append(" and productName like ?");
                list.add("%" + bill.getProductName() + "%");
            }
            if (bill.getProviderId() > 0) {
                sql.append(" and providerId = ?");
                list.add(bill.getProviderId());
            }
            if (bill.getIsPayment() > 0) {
                sql.append(" and isPayment = ?");
                list.add(bill.getIsPayment());
            }

            Object[] params = list.toArray();

            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            while (resultSet.next()) {
                Bill _bill = new Bill();
                _bill.setId(resultSet.getInt("id"));
                _bill.setBillCode(resultSet.getString("billCode"));
                _bill.setProductName(resultSet.getString("productName"));
                _bill.setProductDesc(resultSet.getString("productDesc"));
                _bill.setProductUnit(resultSet.getString("productUnit"));
                _bill.setProductCount(resultSet.getBigDecimal("productCount"));
                _bill.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                _bill.setIsPayment(resultSet.getInt("isPayment"));
                _bill.setProviderId(resultSet.getInt("providerId"));
                _bill.setProviderName(resultSet.getString("providerName"));
                _bill.setCreationDate(resultSet.getTimestamp("creationDate"));
                _bill.setCreatedBy(resultSet.getInt("createdBy"));
                billList.add(_bill);
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return billList;
    }

    /**
     * 通过 ID 删除订单
     *
     * @param connection
     * @param delId
     * @return
     * @throws Exception
     */
    public int deleteBillById(Connection connection, String delId) throws Exception {
        PreparedStatement preparedStatement = null;
        int flag = 0;

        if (connection != null) {
            String sql = "delete from smbms_bill where id = ?";
            Object[] params = {delId};
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 通过 ID 获取订单
     *
     * @param connection
     * @param id
     * @return
     * @throws Exception
     */
    public Bill getBillById(Connection connection, String id) throws Exception {
        Bill bill = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (connection != null) {
            String sql = "select b.*,p.proName as providerName from smbms_bill b, smbms_provider p " +
                    "where b.providerId = p.id and b.id = ?";
            Object[] params = {id};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setBillCode(resultSet.getString("billCode"));
                bill.setProductName(resultSet.getString("productName"));
                bill.setProductDesc(resultSet.getString("productDesc"));
                bill.setProductUnit(resultSet.getString("productUnit"));
                bill.setProductCount(resultSet.getBigDecimal("productCount"));
                bill.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                bill.setIsPayment(resultSet.getInt("isPayment"));
                bill.setProviderId(resultSet.getInt("providerId"));
                bill.setProviderName(resultSet.getString("providerName"));
                bill.setModifyBy(resultSet.getInt("modifyBy"));
                bill.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return bill;
    }


    /**
     * 修改订单信息
     *
     * @param connection
     * @param bill
     * @return
     * @throws Exception
     */
    public int modify(Connection connection, Bill bill) throws Exception {
        int flag = 0;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            String sql = "update smbms_bill set productName = ?," +
                    "productDesc = ?,productUnit = ?,productCount = ?,totalPrice = ?," +
                    "isPayment = ?,providerId = ?,modifyBy = ?,modifyDate = ? where id = ? ";
            Object[] params = {
                    bill.getProductName(), bill.getProductDesc(), bill.getProductUnit(),
                    bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getModifyBy(), bill.getModifyDate(), bill.getId()
            };
            flag = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return flag;
    }

    /**
     * 根据供应商 ID 查询订单数量
     *
     * @param connection
     * @param providerId
     * @return
     * @throws Exception
     */
    public int getBillCountByProviderId(Connection connection, String providerId) throws Exception {
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (connection != null) {
            String sql = "select count(1) as billCount from smbms_bill where providerId = ?";
            Object[] params = {providerId};
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                count = resultSet.getInt("billCount");
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return count;
    }
}
