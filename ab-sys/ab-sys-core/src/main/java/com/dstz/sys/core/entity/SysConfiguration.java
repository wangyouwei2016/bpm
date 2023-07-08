package com.dstz.sys.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dstz.base.entity.AbModel;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lightning
 * @since 2023-05-11
 */
@TableName("sys_configuration")
public class SysConfiguration extends AbModel<SysConfiguration> {

    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 配置类型
     */
    @TableField("conf_type_")
    private String confType;

    /**
     * 配置所属环境
     */
    @TableField("conf_env_")
    private String confEnv;

    /**
     * 配置json
     */
    @TableField("conf_json_")
    private String confJson;

    /**
     * 是否可用
     */
    @TableField("is_enable_")
    private Integer isEnable;

    /**
     * 是否加密
     */
    @TableField("is_encrypt_")
    private Integer isEncrypt;

    /**
     * 创建人
     */
    @TableField(value = "create_by_", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by_", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time_", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public String getConfType() {
        return confType;
    }
    
    public void setConfType(String confType) {
        this.confType = confType;
    }
    
    public String getConfEnv() {
        return confEnv;
    }
    
    public void setConfEnv(String confEnv) {
        this.confEnv = confEnv;
    }
    
    public String getConfJson() {
        return confJson;
    }
    
    public void setConfJson(String confJson) {
        this.confJson = confJson;
    }
    
    public Integer getIsEnable() {
        return isEnable;
    }
    
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
    
    public Integer getIsEncrypt() {
        return isEncrypt;
    }
    
    public void setIsEncrypt(Integer isEncrypt) {
        this.isEncrypt = isEncrypt;
    }
    
    @Override
    public String getCreateBy() {
        return createBy;
    }
    
    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
    public String getUpdateBy() {
        return updateBy;
    }
    
    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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
    public Serializable pkVal() {
        return this.id;
    }
}
