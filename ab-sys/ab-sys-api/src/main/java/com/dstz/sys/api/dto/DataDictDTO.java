package com.dstz.sys.api.dto;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name DataDictDTO
 * @description:
 * @date 2022/2/2317:27
 */
public class DataDictDTO implements Serializable {
    private static final long serialVersionUID = -8765714844119300074L;

    /**
     * ID
     */
    private String id;

    /**
     * 上级id
     */
    private String parentId;

    /**
     * 编码
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * 字典key
     */
    private String dictKey;

    /**
     * 分组字典编码
     */
    private String typeCode;

    /**
     * 排序
     */
    private Integer sn;

    /**
     * dict/node字典项
     */
    private String dictType;

    /**
     * 扩展字段1
     */
    private String extend1;

    /**
     * 扩展字段2
     */
    private String extend2;

    /**
     * 是否系统内置
     */
    private Integer isSystem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }
}
