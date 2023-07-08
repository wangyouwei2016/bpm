package com.dstz.cms.core.entity.dto;

import com.dstz.cms.core.entity.CmsNotify;

import java.util.List;


public class CmsNotifyDTO extends CmsNotify {

    /**
     * 要发布的部门Id集合 (添加用)
     */
    private List<String> groupIdList;


    public CmsNotifyDTO() {
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

}
