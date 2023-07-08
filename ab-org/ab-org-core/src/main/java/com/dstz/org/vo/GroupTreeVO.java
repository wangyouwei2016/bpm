package com.dstz.org.vo;


import com.dstz.base.api.model.Tree;

import java.util.List;

public class GroupTreeVO implements Tree<GroupTreeVO>, java.io.Serializable {

    private String id;
    /**
     * 名字
     */
    private String name;
    /**
     * 父ID
     */
    private String parentId;
    /**
     * 排序
     */
    private Integer sn;
    private String code;
    /**
     * 组织路径
     */
    private String path;
    /**
     * 组织路径
     */
    private String pathName;
    /**
     * 上级组织名称
     */
    private String parentName;
    /**
     * 组织下的子组织
     */
    private List<GroupTreeVO> children;

    public GroupTreeVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupTreeVO() {
    }

    @Override
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

    @Override
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public List<GroupTreeVO> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List list) {
        this.children = list;
    }


}
