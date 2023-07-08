package com.dstz.org.enums;

public enum OrgStatus {

    //    禁用
    DISABLE(0,"禁用","danger"),

    //   启用
    ENABLE(1,"启用", "success");

    private final Integer status;
    private final String desc;
    private String labelCss;

    OrgStatus(Integer status, String desc, String labelCss) {
        this.status = status;
        this.desc = desc;
        this.labelCss = labelCss;
    }

    public String getLabelCss() {
        return labelCss;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
