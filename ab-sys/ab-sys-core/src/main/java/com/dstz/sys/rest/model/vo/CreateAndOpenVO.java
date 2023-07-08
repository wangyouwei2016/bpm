package com.dstz.sys.rest.model.vo;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name createAndOpenVO
 * @description: 创建文档并打开文档结果VO
 * @date 2023/6/215:50
 */
public class CreateAndOpenVO implements Serializable {

    private String url;
    private String fileId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public CreateAndOpenVO(String url, String fileId) {
        this.url = url;
        this.fileId = fileId;
    }

    public CreateAndOpenVO() {
    }

    @Override
    public String
    toString() {
        return "createAndOpenVO{" +
                "url='" + url + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
}
