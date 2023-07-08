package com.dstz.base.query;

/**
 * <pre>
 * 描述：查询字段之间的关系枚举。
 * </pre>
 */
public enum FieldRelation {
    AND("AND"),
    OR("OR"),
    NOT("NOT");
	
    private String key;

    FieldRelation(String key) {
    	this.key = key;
    }

    public String key() {
        return key;
    }
}
