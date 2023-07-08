package com.dstz.sys.api.constant;

/**
 * @author jinxia.hou
 * @Name HolidayConstants
 * @description: 节假日类型枚举
 * @date 2022/3/289:32
 */
public enum HolidayTypeConstants {

    WORKDAY ("DW","工作日"),

    WEEKEND ("DR","周末"),

    LEGAL_HOLIDAY ("LR","法定节假日"),

    LEGAL_WORKDAY ( "LW","法定工作日"),

    COMPANY_HOLIDAY ( "CR","公司节假日"),

    COMPANY_WORKDAY("CW","公司工作日"),
    ;

    private final String type;
    private final String desc;

    HolidayTypeConstants(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
    public String getDesc() {return desc;}
}
