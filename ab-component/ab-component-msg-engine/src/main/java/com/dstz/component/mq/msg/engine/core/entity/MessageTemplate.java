package com.dstz.component.mq.msg.engine.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dstz.base.entity.AbModel;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author lightning
 * @since 2022-11-14
 */
@TableName("ab_message_template")
public class MessageTemplate extends AbModel<MessageTemplate> {

    /**
     * 模板id
     */
    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 模板编码
     */
    @NotEmpty(message = "模板编码不能为空!")
    @TableField("code_")
    private String code;

    /**
     * 模板类型
     */
    @TableField("type_")
    private Integer type;

    /**
     * 模板名称
     */
    @TableField("name_")
    @NotEmpty(message = "模板名称不能为空!")
    private String name;

    /**
     * 模板描述
     */
    @TableField("desc_")
    private String desc;

    /**
     * html模板配置
     */
    @TableField("html_template_")
    private String htmlTemplate;

    /**
     * 卡片模板配置
     */
    @TableField("card_template_")
    private String cardTemplate;

    /**
     * 应用模板配置
     */
    @TableField("app_template_")
    private String appTemplate;

    /**
     * 模板参数
     */
    @TableField("template_param_")
    private String templateParam;

    /**
     * 是否启用
     */
    @TableField("enabled_")
    private Integer enabled;

    /**
     * 是否删除
     */
    @TableField("is_delete_")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 所属组织
     */
    @TableField(value = "create_org_id_", fill = FieldFill.INSERT)
    private String createOrgId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time_", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "updater_", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 更新人ID
     */
    @TableField(value = "update_by_", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public String getCardTemplate() {
        return cardTemplate;
    }

    public void setCardTemplate(String cardTemplate) {
        this.cardTemplate = cardTemplate;
    }

    public String getAppTemplate() {
        return appTemplate;
    }

    public void setAppTemplate(String appTemplate) {
        this.appTemplate = appTemplate;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdater() {
        return updater;
    }

    @Override
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
