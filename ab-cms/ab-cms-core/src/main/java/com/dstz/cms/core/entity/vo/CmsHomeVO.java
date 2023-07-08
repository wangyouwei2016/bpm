package com.dstz.cms.core.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 首页组件
 * </p>
 *
 * @author niu
 * @since 2022-04-11
 */
public class CmsHomeVO implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 图表类型: (内置部件、图表模块、ifream)
     */
    private String type;

    /**
     * 分类类型: 数据字典 :(纯粹自定义的分类筛选用)
     */
    @AbValueMap(type = AbValueMapType.DICT, fixValue = "homeType", matchField = "code", attrMap = @AbValueMap.AttrMap(originName = "name"))
    private String typeCode;

    /**
     * 是否启用(0禁用  1启用)
     */
    private Integer enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建人姓名
     */
    private String creator;


    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
