package com.dstz.sys.rest.model.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name OnlineDocJsonParamDTO
 * @description: 在线文档请求接口DTO
 * @date 2023/5/2410:31
 */
public class OperateOnlineDocDTO implements Serializable {
    /**
     * 文件id
     */
    @NotNull(message = "文件Id 不能为空")
    private String fileId;

    /**
     * 文件名，创建文件的时候必填
     */
    private String fileName;

    /**
     * 用权限
     */
    private Integer userRight;
    /**
     * 是否自动保存，默认是false
     */
    private Boolean saveFlag;

    /**
     * 额外参数
     */
    private Object extraData;
    /**
     * 按钮权限
     */
    private String userMenuPermission;

    public OperateOnlineDocDTO(String fileId) {
        this.fileId = fileId;
    }

    public OperateOnlineDocDTO(String fileId, Integer userRight) {
        this.fileId = fileId;
        this.userRight = userRight;
    }

    public OperateOnlineDocDTO() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getUserRight() {
        return userRight;
    }

    public void setUserRight(Integer userRight) {
        this.userRight = userRight;
    }

    public Boolean getSaveFlag() {
        if (saveFlag == null){
            return  Boolean.FALSE;
        }
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUserMenuPermission(String userMenuPermission) {
        this.userMenuPermission = userMenuPermission;
    }

    @Override
    public String toString() {
        return "OperateOnlineDocDTO{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userRight=" + userRight +
                ", saveFlag=" + saveFlag +
                ", extraData=" + extraData +
                ", userMenuPermission='" + userMenuPermission + '\'' +
                '}';
    }
}
