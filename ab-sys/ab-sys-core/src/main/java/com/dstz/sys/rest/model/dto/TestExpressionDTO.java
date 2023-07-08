package com.dstz.sys.rest.model.dto;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name TestExpressionDTO
 * @description: 测试日志表达式Dto
 * @date 2022/3/310:41
 */
public class TestExpressionDTO implements Serializable {
    private static final long serialVersionUID = -5631456838690512653L;

    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 响应体
     */
    private String responseBody;

    /**
     * 表达式
     */
    private String expressionString;

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getExpressionString() {
        return expressionString;
    }

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }
}
