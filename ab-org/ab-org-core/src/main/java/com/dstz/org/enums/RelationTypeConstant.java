package com.dstz.org.enums;

import com.dstz.org.api.enums.GroupType;

/**
 * 组织级别
 *
 * @author jeff
 */
public enum RelationTypeConstant {

    /**
     * 用户与组
     */
    GROUP_USER("groupUser", "用户与组"),

    /**
     * 岗位
     */
    POST("groupRole", "岗位"),

    /**
     * 用户与角色
     */
    USER_ROLE("userRole", "用户与角色"),

    /**
     * 岗位用户
     */
    POST_USER("groupUserRole", "岗位用户");

    private final String key;
    private final String label;

    RelationTypeConstant(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String label() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public String getKey() {
        return key;
    }


    /**
     * 通过组类型转换成与用户的关系类型
     *
     * @param groupType 组类型
     * @return 关联类型
     */
    public static RelationTypeConstant getUserRelationTypeByGroupType(String groupType) {
        switch (GroupType.fromType(groupType)) {
            case ORG:
                return RelationTypeConstant.GROUP_USER;
            case POST:
                return RelationTypeConstant.POST_USER;
            case ROLE:
                return RelationTypeConstant.USER_ROLE;
            default:
                return null;
        }
    }

}
