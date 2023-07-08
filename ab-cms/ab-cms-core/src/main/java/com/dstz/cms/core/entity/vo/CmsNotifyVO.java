package com.dstz.cms.core.entity.vo;

import com.dstz.cms.core.entity.CmsComments;
import com.dstz.cms.core.entity.CmsNotify;
import com.dstz.cms.core.entity.CmsNotifyShare;

import java.util.List;


public class CmsNotifyVO extends CmsNotify {

    /**
     * 已读状态 (0:未读, 1已读, 2过期)
     */
    protected Integer isRead;

    /**
     * 公告关联的部门信息 (展示用)
     */
    private List<CmsNotifyShare> cmsNotifyShareList;

    /**
     * 公告关联的评论信息 (展示用)
     */
    private List<CmsComments> cmsCommentsList;


    public CmsNotifyVO() {
    }

    public List<CmsComments> getCmsCommentsList() {
        return cmsCommentsList;
    }

    public void setCmsCommentsList(List<CmsComments> cmsCommentsList) {
        this.cmsCommentsList = cmsCommentsList;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public List<CmsNotifyShare> getCmsNotifyShareList() {
        return cmsNotifyShareList;
    }

    public void setCmsNotifyShareList(List<CmsNotifyShare> cmsNotifyShareList) {
        this.cmsNotifyShareList = cmsNotifyShareList;
    }

}
