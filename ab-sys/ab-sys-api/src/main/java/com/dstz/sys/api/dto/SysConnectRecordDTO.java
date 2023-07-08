package com.dstz.sys.api.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name SysConnectRecordDTO
 * @description: 公共业务关联记录DTO
 * @date 2022/3/815:44
 */
public class SysConnectRecordDTO implements Serializable {
    private static final long serialVersionUID = 8745568231922260290L;

    /**
     * 类型
     */
    @NotEmpty(message = "关联类型不能为空")
    protected String type;
    /**
     * 源ID
     */
    @NotEmpty(message = "关联源头ID不能为空")
    protected String sourceId;
    /**
     * 关联ID
     */
    @NotEmpty(message = "关联目标ID不能为空")
    protected String targetId;
    /**
     * 提示信息
     */
    @NotEmpty(message = "关联提示信息不能为空")
    protected String notice;


    public void setType(String type) {
        this.type = type;
    }

    /**
     * 返回 类型
     *
     * @return
     */
    public String getType() {
        return this.type;
    }


    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 返回 源ID
     *
     * @return
     */
    public String getSourceId() {
        return this.sourceId;
    }


    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 返回 关联ID
     *
     * @return
     */
    public String getTargetId() {
        return this.targetId;
    }


    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * 返回 提示信息
     *
     * @return
     */
    public String getNotice() {
        return this.notice;
    }
}
