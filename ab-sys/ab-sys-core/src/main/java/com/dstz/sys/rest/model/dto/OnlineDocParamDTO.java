package com.dstz.sys.rest.model.dto;

import com.dstz.sys.api.constant.OnlineDocMethod;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name OnlinDocParamDTO
 * @description: 文档操作参数DTO
 * @date 2023/5/249:58
 */
public class OnlineDocParamDTO implements Serializable {

    private Integer method;
    private OnlineDocParam params;

    public Integer getMethod() {
        return method;
    }

    public void setMethod(OnlineDocMethod method) {
        this.method = method.getKey();
    }

    public OnlineDocParam getParams() {
        return params;
    }

    public void setParams(OnlineDocParam params) {
        this.params = params;
    }

    public OnlineDocParamDTO() {
    }

    public OnlineDocParamDTO(OnlineDocMethod method, OnlineDocParam params) {
        this.method = method.getKey();
        this.params = params;
    }

    @Override
    public String toString() {
        return "OnlineDocParamDTO{" +
                "method=" + method +
                ", params=" + params +
                '}';
    }
}
