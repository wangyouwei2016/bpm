package com.dstz.auth.forbidden;

import com.dstz.auth.authentication.api.constant.AuthApiConstant;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回无权限
 *
 * @author lightning
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        // 登录超时记录下当前redirectURL
        request.getSession().setAttribute(AuthApiConstant.SSO_REDIRECTURL, getBackUrl(request));

        byte[] dataBytes = JsonUtils.toJSONString(ApiResponse.fail(AuthStatusCode.LOGIN_TIMEOUT.getCode(), ex.getMessage())).getBytes();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentLength(dataBytes.length);
        response.getOutputStream().write(dataBytes);
    }

    private String getBackUrl(HttpServletRequest request) {
        //服务器地址
        StringBuffer strBackUrl = new StringBuffer("http://").append(request.getServerName())

                .append(":")

                .append(request.getServerPort()) //端口号

                .append(request.getContextPath())     //项目名称

                .append(request.getServletPath())     //请求页面或其他地址

                .append("?").append((request.getQueryString()));


        return strBackUrl.toString();
    }

}