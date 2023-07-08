package com.dstz.base.common.codes;

import cn.hutool.core.util.StrUtil;
import com.dstz.base.api.vo.ApiResponse;
import org.slf4j.helpers.MessageFormatter;

/**
 * <pre>
 * 系统接口响应码 定义
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2022-01-22
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public interface IBaseCode {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    String getCode();

    /**
     * 获取消息，支持参数格式化，用法上与slf4j相同
     *
     * @return 状态信息模式
     */
    String getMessage();

    /**
     * 格式化默认消息
     *
     * @param arguments 参数项
     * @return 新BaseCode
     */
    default IBaseCode formatDefaultMessage(Object... arguments) {
        return formatMessage(getMessage(), arguments);
    }

    /**
     * 格式化消息，参数支持格式化模式，用法上与slf4j相同
     *
     * @param messagePattern 获取消息模式，可做参数格式化，用法上与slf4j相同
     * @param arguments      参数项
     * @return 新BaseCode
     */
    @Deprecated
    default IBaseCode formatMessage(String messagePattern, Object... arguments) {
        return newBuilder().withCode(getCode()).withMessage(messagePattern, arguments).build();
    }
    
    /**
     * <pre>
     * 利用hutool的StrUtil.format格式化信息
     * eg：
     * xxx.formatHutoolMessage("a={},b={}", a, b)
     * </pre>	
     * 
     * @param messagePattern
     * @param arguments
     * @return
     */
    default IBaseCode formatHutoolMessage(String messagePattern, Object... arguments) {
        return formatDefaultMessage(StrUtil.format(messagePattern, arguments));
    }

    /**
     * 新建构建器
     *
     * @return 构建器
     */
    static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 构建器
     */
    class Builder {

        private final BaseCodeImpl baseCode = new BaseCodeImpl();

        /**
         * 使用状态码
         *
         * @param code 状态码
         * @return 构建器
         */
        public Builder withCode(String code) {
            baseCode.setCode(code);
            return this;
        }

        /**
         * 使用消息
         *
         * @param format    格式化，与slf4j logger使用方式一致
         * @param arguments 格式化参数
         * @return 构建器
         */
        public Builder withMessage(String format, Object... arguments) {
            if (arguments == null || arguments.length == 0) {
                baseCode.setMessage(format);
            } else {
                baseCode.setMessage(MessageFormatter.arrayFormat(format, arguments).getMessage());
            }
            return this;
        }

        /**
         * 构建出响应码
         *
         * @return 响应码
         */
        public IBaseCode build() {
            return baseCode;
        }
    }

    /**
     * 构建接口响应
     *
     * @param <T> 接口响应体
     * @return 接口响应
     */
    default <T> ApiResponse<T> buildApiResponse() {
        return ApiResponse.fail(getCode(), getMessage());
    }
}
