package com.dstz.org.api;

import com.dstz.org.api.model.IUser;

/**
 * 针对用户操作openId接口
 */
public interface UserOpenIdApi {

    /**
     * 根据用户openId获取用户
     *
     * @param openId 用户openId
     * @return 用户
     */
    IUser getByOpenId(String openId);

    /**
     * 设置用户openId
     * @param account
     * @param openId
     */
    void saveOpenIdByAccount(String account,String openId);
}
