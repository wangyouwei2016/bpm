package com.dstz.base.api.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 批量数据传输对象
 *
 * @author wacxhs
 * @since 2022-01-24
 */
public class IdBatchDTO implements java.io.Serializable {


    private static final long serialVersionUID = 2403594070347388576L;

    /**
     * 批量ID
     */
    @NotEmpty(message = "批量ID不能为空")
    private List<Serializable> ids;

    public List<Serializable> getIds() {
        return ids;
    }

    public void setIds(List<Serializable> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "IdBatchDTO{" +
                "ids=" + ids +
                '}';
    }
}
