package com.dstz.org.enums;

public enum OrgMaster {

    //    禁用
    DEFAULT (0,"否","danger"),

    //   启用
    MASTER(1,"是","success");



    private final Integer master;
    private final String desc;
    private String labelCss;

    OrgMaster(Integer master, String desc, String labelCss) {
        this.master = master;
        this.desc = desc;
        this.labelCss = labelCss;
    }

    public Integer getMaster() {
        return master;
    }

    public String getDesc() {
        return desc;
    }

    public String getLabelCss() {
        return labelCss;
    }

}
