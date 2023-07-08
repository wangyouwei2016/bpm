package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class SaveRoleUsersDTO implements java.io.Serializable {

    /**
     * 角色ID
     */
    @NotBlank(message = "参数不能为空！")
    private String roleId;

    @NotEmpty(message = "请选择用户！")
    private String[] userIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }
}
