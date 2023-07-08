package com.dstz.cms.core.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 知识库	
 * </p>
 *
 * @author niuniu
 * @since 2022-08-19
 */
public class CmsMyDocumentVO implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 文档名
     */
    private String name;

    /**
     * 父目录ID
     */
    private String parentId;

    /**
     * 父目录名称
     */
    private String parentName;

    /**
     * 关联文件名称
     */
    private String fileType;

    /**
     * 继承id (继承=parentId,  非继承=自己id)
     */
    private String rightsId;

    /**
     * 阅读数量
     */
    private Integer readNum;

    /**
     * 借阅人数
     */
    private Integer borrowNum;

    /**
     * 权限名称(避免联查仅做展示,关系在权限表维护)
     */
    private String rightsName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 来自(收藏, 我的, 借阅中, 借阅通过)
     */
    private String sources;

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRightsId() {
        return rightsId;
    }

    public void setRightsId(String rightsId) {
        this.rightsId = rightsId;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(Integer borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getRightsName() {
        return rightsName;
    }

    public void setRightsName(String rightsName) {
        this.rightsName = rightsName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }
}
