package com.dstz.org.dto;


import javax.validation.constraints.NotBlank;
import java.util.List;


public class SaveGroupDTO implements java.io.Serializable {

    private String id;

    /**
     * 名字
     */
    @NotBlank(message = "组织名称不能为空")
    private String name;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 排序
     */

    private Integer sn;

    @NotBlank(message = "组织编码不能为空")
    private String code;

    /**
     * 类型：0集团，1公司，3部门
     */
    private String type;

    /**
     * 描述
     */
    private String desc;

    /**
     * 组织路径
     */
    private String path;


    /**
     * 岗位
     */
    private List<OrgRelationDTO> orgRelationList;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<OrgRelationDTO> getOrgRelationList() {
        return orgRelationList;
    }

    public void setOrgRelationList(List<OrgRelationDTO> orgRelationList) {
        this.orgRelationList = orgRelationList;
    }

}
