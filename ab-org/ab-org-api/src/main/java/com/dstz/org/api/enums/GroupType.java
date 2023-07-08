package com.dstz.org.api.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * 组类型
 *
 * @author wacxhs
 */
public enum GroupType {

    /**
     * 组织
     */
    ORG("org", "组织"),

    /**
     * 角色
     */
    ROLE("role", "角色"),

    /**
     * 岗位
     */
    POST("post", "岗位");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String desc;

    GroupType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 匹配type，如果type未匹配到返回null
     *
     * @param type 类型
     * @return 组类型
     */
    public static GroupType fromTypeIfNull(String type) {
        Optional<GroupType> groupType = Arrays.stream(values()).filter(item -> item.getType().equalsIgnoreCase(type)).findFirst();
        return groupType.orElse(null);
    }

    /**
     * 匹配type，如果type抛出异常
     *
     * @param type 类型
     * @return 组类型
     */
    public static GroupType fromType(String type) throws IllegalArgumentException {
        GroupType groupType = fromTypeIfNull(type);
        if (groupType == null) {
            throw new IllegalArgumentException(String.format("%s undefined", type));
        }
        return groupType;
    }
}
