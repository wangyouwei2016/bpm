package com.dstz.sys.api.constant;

/**
 * @author jinxia.hou
 * @Name OnlineDocMethod
 * @description: 在线文档操作方法
 * @date 2023/5/2415:58
 */
public enum OnlineDocMethod {
    OPEN(1,"/api.do","打开文档"),
    SAVE(2,"/api.do","保存文档"),
    CLOSE(3,"/api.do","关闭文档"),
    IS_OPEN(4,"/api.do","判断文档是否打开"),

    ;

    OnlineDocMethod(Integer key,String path,String desc) {
        this.desc = desc;
        this.key = key;
        this.path = path;
    }



    private String desc;
    private String path;
    private Integer key;

    public Integer getKey() {
        return key;
    }
    public String getPath() {
        return path;
    }
}
