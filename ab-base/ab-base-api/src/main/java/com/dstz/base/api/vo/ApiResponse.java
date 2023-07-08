package com.dstz.base.api.vo;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.service.Executable;

/**
 * <pre>
 * 全局接口响应定义
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2022-01-22
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public class ApiResponse<T> {

    /**
     * 状态码-成功
     */
    public static final String CODE_SUCCESS = "Success";

    /**
     * 状态吗-成功-消息
     */
    public static final String CODE_SUCCESS_MESSAGE = "操作成功";
    
    /**
     * 是否成功
     */
    private Boolean isOk;

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String message;

	/**
     * 响应体
     */
    private T data;

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean ok) {
        isOk = ok;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getMessage() {
		return message;
	}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 使用是否成功
     *
     * @param isOk 是否成功
     * @return 接口响应
     */
    public ApiResponse<T> withIsOk(Boolean isOk) {
        this.isOk = isOk;
        return this;
    }

    /**
     * 使用状态码
     *
     * @param code 状态码
     * @return 接口响应
     */
    public ApiResponse<T> withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * 使用状态消息
     *
     * @param msg 状态消息
     * @return 接口响应
     */
    public ApiResponse<T> withMessage(String msg) {
        this.message = msg;
        return this;
    }

    /**
     * 使用数据
     *
     * @param data 数据
     * @return 接口响应
     */
    public ApiResponse<T> withData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 成功响应
     *
     * @param <T> T
     * @return 接口响应
     */
    public static <T> ApiResponse<T> success() {
        return success((T) null);
    }

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  T
     * @return 接口响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>().withIsOk(Boolean.TRUE).withCode(CODE_SUCCESS).withMessage(CODE_SUCCESS_MESSAGE).withData(data);
    }

	public static <T> ApiResponse<PageListVO<T>> success(PageListDTO<T> data) {
        return new ApiResponse<PageListVO<T>>()
        		.withIsOk(Boolean.TRUE)
        		.withCode(CODE_SUCCESS)
        		.withMessage(CODE_SUCCESS_MESSAGE)
        		.withData(new PageListVO<T>(data));
    }

    /**
     * 成功响应 (没有返回值)
     *
     * @param executable 执行者，不返回任何数据(例如:增删改)
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(Executable executable) {
        executable.execute();
        //因操作返回从英文换成中文,提升体验
        return  ApiResponse.<T>success().withMessage(CODE_SUCCESS_MESSAGE);
    }

    /**
     * 失败响应
     *
     * @param code    响应码
     * @param message 响应信息
     * @param <T>     T
     * @return 接口响应
     */
    public static <T> ApiResponse<T> fail(String code, String message) {
        return fail(code, message, null);
    }

    /**
     * 失败响应
     *
     * @param code    响应码
     * @param message 响应信息
     * @param body    内容体
     * @param <T>     T
     * @return 接口响应
     */
    public static <T> ApiResponse<T> fail(String code, String message, T body) {
        return new ApiResponse<T>().withIsOk(Boolean.FALSE).withCode(code).withMessage(message).withData(body);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "isOk=" + isOk +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
