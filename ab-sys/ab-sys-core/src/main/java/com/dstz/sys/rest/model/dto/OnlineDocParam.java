package com.dstz.sys.rest.model.dto;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name OnlineDocParam
 * @description: 在线文档参数DTO
 * @date 2023/5/249:59
 */
public class OnlineDocParam implements Serializable {
    /**
     * 用户id
     */
   private String userId;
    /**
     * 用户名称
     */
   private String userName;
    /**
     * 用户头像
     */
   private String userAvatar;
    /**
     * 文件id
     */
   private String fileId;
    /**
     * 文件名称
     */
   private String fileName;
    /**
     * 文件path
     */
   private String filePath;
    /**
     * 用权限
     */
   private Integer userRight;
    /**
     * 是否自动保存，默认是false
     */
   private Boolean saveFlag;
    /**
     * 回调url
     */
   private String callbackUrl;
    /**
     * 额外参数
     */
   private Object extraData;
    /**
     * 按钮权限
     */
   private String userMenuPermission;

    public OnlineDocParam(String fileId) {
        this.fileId = fileId;
    }

    public OnlineDocParam() {
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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getUserRight() {
        return userRight;
    }

    public void setUserRight(Integer userRight) {
        this.userRight = userRight;
    }

    public Boolean getSaveFlag() {
        if(saveFlag == null){
            return Boolean.FALSE;
        }
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public String getUserMenuPermission() {
        return userMenuPermission;
    }

    public void setUserMenuPermission(String userMenuPermission) {
        this.userMenuPermission = userMenuPermission;
    }

    @Override
    public String toString() {
        return "OnlineDocParam{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", userRight=" + userRight +
                ", saveFlag=" + saveFlag +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", extraData=" + extraData +
                ", userMenuPermission='" + userMenuPermission + '\'' +
                '}';
    }
}
