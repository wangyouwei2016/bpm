package com.dstz.base.common.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author jinxia.hou
 * @Name EnvironmentConstant
 * @description: 环境常量
 * @date 2022/2/1416:14
 */
public enum EnvironmentConstants {

    DEV("DEV", ",开发-默认"),
    SIT("SIT", "测试"),
    DEMO("DEMO", "案例"),
    UAT("UAT", "用户测试"),
    GRAY("GRAY", "灰度"),
    PROD("PROD", "生产");


    private final String key;
    private final String value;

    EnvironmentConstants(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getKes() {
        StringBuilder sb = new StringBuilder();
        for (EnvironmentConstants e : EnvironmentConstants.values()) {
            sb.append("[").append(e.key).append("]");
        }
        return sb.toString();
    }

    public static boolean contain(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }

        for (EnvironmentConstants e : EnvironmentConstants.values()) {
            if (key.equals(e.key)) {
                return true;
            }
        }
        return false;
    }
}
