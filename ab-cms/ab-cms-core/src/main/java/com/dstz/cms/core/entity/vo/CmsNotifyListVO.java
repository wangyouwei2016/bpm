package com.dstz.cms.core.entity.vo;

import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;

import java.io.Serializable;
import java.util.Date;


public class CmsNotifyListVO implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型code
     */
    @AbValueMap(type = AbValueMapType.DICT, fixValue = "gglx", matchField = "code", attrMap = @AbValueMap.AttrMap(originName = "name"))
    private String typeId;

    /**
     * 已读状态 (0:未读, 1已读, 2过期)
     */
    private Integer isRead;

    /**
     * 评论数量
     */
    private Integer commentsNum;

    /**
     * 访问数量
     */
    private Integer visitNum;

    /**
     * 发布时间或下架时间根据状态来区分
     */
    private Date releaseTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

}
