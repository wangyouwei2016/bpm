package com.dstz.base.common.requestlog;

import cn.hutool.core.map.MapUtil;
import com.dstz.base.common.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 请求日志
 *
 * @author wacxhs
 */
public class AbRequestLog implements java.io.Serializable {

    private static final long serialVersionUID = -3755749914936860032L;

    /**
     * 原始请求体
     */
    public static final String REQUEST_BODY_RAW = "requestBodyRaw";
    
    /**
     * trace id
     */
    private String traceId;

    /**
     * 操作用户ID
     */
    private String userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作部门名称
     */
    private String groupName;

    /**
     * 操作部门Id
     */
    private String groupId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 后端路径
     */
    private String pathPattern;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 请求头
     */
    private Map<String, String> requestHeaderMap;

    /**
     * 请求参数
     */
    private Map<String, String> requestParameterMap;

    /**
     * 请求体
     */
    private Object requestBody;

    /**
     * 响应体
     */
    private Object responseBody;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 耗时毫秒
     */
    private Long durationMs;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 绑定属性，用于前置事件属性绑定
     */
    @JsonIgnore
    @Transient
    private Map<Object, Object> attributeMap;

    public String getTraceId() {
        return traceId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public Map<String, String> getRequestHeaderMap() {
        return requestHeaderMap;
    }

    public Map<String, String> getRequestParameterMap() {
        return requestParameterMap;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public Throwable getException() {
        return exception;
    }

    /**
     * 绑定属性
     *
     * @param key   属性KEY
     * @param value 属性值
     */
    public void bindAttribute(Object key, Object value) {
        if (attributeMap == null) {
            attributeMap = MapUtil.newHashMap();
        }
        attributeMap.put(key, value);
    }

    /**
     * 获取属性值
     *
     * @param key 属性KEY
     * @return 属性值
     */
    public Object getAttribute(Object key) {
        return Objects.isNull(attributeMap) ? null : attributeMap.get(key);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * 实例化Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {

        private final AbRequestLog abRequestLog = new AbRequestLog();

        private Builder() {
        }

        public Builder withTraceId(String traceId) {
            this.abRequestLog.traceId = traceId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.abRequestLog.userId = userId;
            return this;
        }

        public Builder withUsername(String username) {
            this.abRequestLog.username = username;
            return this;
        }

        public Builder withGroupId(String groupId) {
            this.abRequestLog.groupId = groupId;
            return this;
        }

        public Builder withGroupName(String groupName) {
            this.abRequestLog.groupName = groupName;
            return this;
        }

        public Builder withClientIp(String clientIp) {
            this.abRequestLog.clientIp = clientIp;
            return this;
        }

        public Builder withRequestMethod(String requestMethod) {
            this.abRequestLog.requestMethod = requestMethod;
            return this;
        }

        public Builder withUrl(String url) {
            this.abRequestLog.url = url;
            return this;
        }


        public Builder withPathPattern(String pathPattern) {
            this.abRequestLog.pathPattern = pathPattern;
            return this;
        }

        public Builder withRequestTime(Date requestTime) {
            this.abRequestLog.requestTime = requestTime;
            return this;
        }

        public Builder withRequestHeaderMap(Map<String, String> requestHeaderMap) {
            this.abRequestLog.requestHeaderMap = requestHeaderMap;
            return this;
        }

        public Builder withRequestParameterMap(Map<String, String> requestParameterMap) {
            this.abRequestLog.requestParameterMap = requestParameterMap;
            return this;
        }

        public Builder withRequestBody(Object requestBody) {
            this.abRequestLog.requestBody = requestBody;
            if(requestBody != null){
                // 复制请求体副本
                this.abRequestLog.bindAttribute(REQUEST_BODY_RAW, JsonUtils.parseObject(JsonUtils.toJSONString(requestBody), Object.class));
            }
            return this;
        }

        public Builder withResponseBody(Object responseBody) {
            this.abRequestLog.responseBody = responseBody;
            return this;
        }

        public Builder withResponseTime(Date responseTime) {
            this.abRequestLog.responseTime = responseTime;
            this.abRequestLog.durationMs = (responseTime.getTime() - abRequestLog.getRequestTime().getTime());
            return this;
        }

        public Builder withException(Throwable throwable) {
            this.abRequestLog.exception = throwable;
            return this;
        }

        public AbRequestLog build() {
            return abRequestLog;
        }
    }
}
