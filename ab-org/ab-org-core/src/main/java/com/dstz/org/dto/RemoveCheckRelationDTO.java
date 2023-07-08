package com.dstz.org.dto;

public class RemoveCheckRelationDTO implements java.io.Serializable {


    /**
     * 组ID
     */
    private String groupId;
    /**
     * 角色ID
     */
    private String roleId;

    public RemoveCheckRelationDTO() {
    }

    public RemoveCheckRelationDTO(String groupId, String roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
