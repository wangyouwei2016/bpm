package com.dstz.cms.core.entity.dto;

import com.dstz.base.api.dto.QueryParamDTO;

/**
 * 查询条件的入参
 * @author Jeff
 *
 */
public class CmsQueryParamDTO extends QueryParamDTO {
	
    /**
     * 查询站内信的分页类型 (0通知, 1待办)
     */
    private int type;


    public CmsQueryParamDTO(int type) {
        this.type = type;
    }

    public CmsQueryParamDTO() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
