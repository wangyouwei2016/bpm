package com.dstz.sys.rest.model.vo;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name SysAuditLogMetadataCacheVO
 * @description:
 * @date 2022/2/2315:40
 */
public class SysAuditLogMetadataCacheVO implements Serializable {

    private static final long serialVersionUID = -1961365464945900536L;
    /**
     * 元数据主键编号
     */
    private String id;

    /**
     * 判断条件表达式
     */
    private String predicateExpr;


    /**
     * 业务主键获取表达式
     */
    private String bizIdExpr;

    /**
     * 记录数据获取表达式
     */
    private String dataExpr;

    /**
     * 日志描述模板，支持SPEL表达式
     */
    private String descriptionTpl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPredicateExpr() {
        return predicateExpr;
    }

    public void setPredicateExpr(String predicateExpr) {
        this.predicateExpr = predicateExpr;
    }

    public String getBizIdExpr() {
        return bizIdExpr;
    }

    public void setBizIdExpr(String bizIdExpr) {
        this.bizIdExpr = bizIdExpr;
    }

    public String getDataExpr() {
        return dataExpr;
    }

    public void setDataExpr(String dataExpr) {
        this.dataExpr = dataExpr;
    }

    public String getDescriptionTpl() {
        return descriptionTpl;
    }

    public void setDescriptionTpl(String descriptionTpl) {
        this.descriptionTpl = descriptionTpl;
    }
}
