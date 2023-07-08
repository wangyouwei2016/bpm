package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;

public class SaveRoleDTO implements java.io.Serializable {

    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "名称不能为空！")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空！")
    private String code;

    /**
     * 0：禁用，1：启用
     */
    private Integer enabled;

    /**
     * 角色等级
     */
    private Integer level;

    /**
     * 描述
     */
    private String desc;

    /**
     * 分类字典编码
     */
    @NotBlank(message = "分组不能为空！")
    private String typeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return "SaveRoleDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", enabled=" + enabled +
                ", level=" + level +
                ", desc='" + desc + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}
