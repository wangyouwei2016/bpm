package com.dstz.auth.forbidden;

import cn.hutool.core.util.StrUtil;
import com.dstz.auth.authentication.api.constant.AuthApiConstant;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 超时访问
 *
 * @author lightning
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 登录超时记录下当前redirectURL
        String referer = request.getHeader("Referer");
        String Url = getBackUrl(request);
        if (StrUtil.isNotEmpty(referer) && StrUtil.isNotEmpty(request.getContentType())) {
            request.getSession().setAttribute(AuthApiConstant.SSO_REDIRECTURL, referer);
        }

        byte[] dataBytes = JsonUtils.toJSONString(ApiResponse.fail(GlobalApiCodes.ACCESS_FORBIDDEN.getCode(), "登录访问超时！")).getBytes();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentLength(dataBytes.length);
        response.getOutputStream().write(dataBytes);
    }

    private String getBackUrl(HttpServletRequest request) {
        //服务器地址
        StringBuffer strBackUrl = new StringBuffer("http://").append(request.getServerName())

                .append(":")
                //端口号
                .append(request.getServerPort())
                //项目名称
                .append(request.getContextPath())

                .append(request.getServletPath());

        if (StrUtil.isNotEmpty(request.getQueryString())) {
            strBackUrl.append("?").append((request.getQueryString()));
        }


        return strBackUrl.toString();
    }


}