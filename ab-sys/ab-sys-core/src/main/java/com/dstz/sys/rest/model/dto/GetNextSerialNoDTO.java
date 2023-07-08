package com.dstz.sys.rest.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * @author jinxia.hou
 * @Name getNextSerialNoDTO
 * @description: 获取下一个流水号DTO
 * @date 2022/9/1611:10
 */
public class GetNextSerialNoDTO implements Serializable {
    private static final long serialVersionUID = -3420062216474976137L;

    @NotBlank
    private String code;

    private String param;

    private Map<String,Object> paramMap;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return "getNextSerialNoDTO{" +
                "code='" + code + '\'' +
                ", param='" + param + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
