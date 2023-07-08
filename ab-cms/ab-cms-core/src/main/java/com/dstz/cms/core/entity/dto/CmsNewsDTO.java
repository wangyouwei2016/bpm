package com.dstz.cms.core.entity.dto;

import com.dstz.cms.core.entity.CmsComments;
import com.dstz.cms.core.entity.CmsNews;

import java.util.List;

public class CmsNewsDTO extends CmsNews {
    /**
     * 公告关联的评论信息 (展示用)
     */
    private List<CmsComments> cmsCommentsList;

    public List<CmsComments> getCmsCommentsList() {
        return cmsCommentsList;
    }

    public void setCmsCommentsList(List<CmsComments> cmsCommentsList) {
        this.cmsCommentsList = cmsCommentsList;
    }

    public CmsNewsDTO() {
    }

    public CmsNewsDTO(List<CmsComments> cmsCommentsList) {
        this.cmsCommentsList = cmsCommentsList;
    }
}
