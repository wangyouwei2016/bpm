package com.dstz.sys.rest.model.vo;

import com.dstz.base.api.model.Tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author niuniu
 * @description: 日志模块左侧树的VO类
 * @date 2022/2/2315:40
 */
public class SysAuditLogTreeVO implements Tree, Serializable {

    /**
     * 唯一标识
     */
    private String id;
    /**
     * 仅作list转树筛选用, 无其他意义
     */
    private String code;
    /**
     * 展示名称
     */
    private String name;

    /**
     * 上级id
     */
    private String parentId;

    /**
     * 子类集合
     */
    private List<SysAuditLogTreeVO> children;


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

    @Override
    public List<SysAuditLogTreeVO> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SysAuditLogTreeVO(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
