package com.dstz.sys.rest.model.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * @author jinxia.hou
 * @Name SysWorkHandoverEditDTO
 * @description:
 * @date 2022/2/2210:52
 */
public class SysWorkHandoverEditDTO implements Serializable {

    private static final long serialVersionUID = 8974946297588709585L;
    /**
     * 用户编号
     */
    private String userId;

    /**
     * 接收用户编号
     */
    private Set<String> receiveUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Set<String> receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
