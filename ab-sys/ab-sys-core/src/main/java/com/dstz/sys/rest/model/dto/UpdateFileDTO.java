package com.dstz.sys.rest.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name UpdateFileDTO
 * @description: 更新文件dto
 * @date 2023/5/2417:17
 */
public class UpdateFileDTO implements Serializable {
    private String fileId;
    private String userId;
    private String userName;
    private InputStream fileStream;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    @Override
    public String toString() {
        return "UpdateFileDTO{" +
                "fileId='" + fileId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", file=" + fileStream +
                '}';
    }
}
