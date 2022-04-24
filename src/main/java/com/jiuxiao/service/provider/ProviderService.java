package com.jiuxiao.service.provider;

import com.jiuxiao.pojo.Provider;

import java.util.List;

/**
 * 供应商业务层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/24 20:30
 * @since 1.0.0
 */
public interface ProviderService {
    /**
     * 供应商列表
     *
     * @param proName
     * @return
     */
    public List<Provider> getProviderList(String proName, String proCode);

    /**
     * 增加供应商
     *
     * @param provider
     * @return
     */
    public boolean add(Provider provider);

    /**
     * 删除供应商
     *
     * @param delId
     * @return
     */
    public int deleteProviderById(String delId);

    /**
     * 修改供应商信息
     *
     * @param provider
     * @return
     */
    public boolean modify(Provider provider);

    /**
     * 获取供应商信息
     *
     * @param id
     * @return
     */
    public Provider getProviderById(String id);
}
