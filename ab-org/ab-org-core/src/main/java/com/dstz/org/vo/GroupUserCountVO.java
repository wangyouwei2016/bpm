package com.dstz.org.vo;

public class GroupUserCountVO {

    private  String  id;

    /**
     * 组织用户数量
     */
    private Integer userCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
}
