package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;

public class BatchSaveRelationDTO implements java.io.Serializable {

    /**
     * 组ID
     */
    @NotBlank(message = "组织不能为空")
    private String groupIds;

    /**
     * 角色ID
     */
    @NotBlank(message = "角色不能为空")
    private String roleIds;

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
