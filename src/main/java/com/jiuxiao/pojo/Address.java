package com.jiuxiao.pojo;

import java.util.Date;

/**
 * 地址信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:34
 * @since 1.0.0
 */
public class Address {

    /**
     * 主键 ID
     */
    private Integer id;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 收货地址明细
     */
    private String addressDesc;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改者
     */
    private Integer modifyBy;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 用户 ID
     */
    private Integer userId;

    public Address() {
    }

    public Address(Integer id, String contact, String addressDesc, String postCode, String tel, Integer createBy,
                   Date creationDate, Integer modifyBy, Date modifyDate, Integer userId) {
        this.id = id;
        this.contact = contact;
        this.addressDesc = addressDesc;
        this.postCode = postCode;
        this.tel = tel;
        this.createBy = createBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", addressDesc='" + addressDesc + '\'' +
                ", postCode='" + postCode + '\'' +
                ", tel='" + tel + '\'' +
                ", createBy=" + createBy +
                ", creationDate=" + creationDate +
                ", modifyBy=" + modifyBy +
                ", modifyDate=" + modifyDate +
                ", userId=" + userId +
                '}';
    }
}
