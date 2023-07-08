package com.dstz.auth.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.dstz.auth.utils.IngoreChecker;
import com.dstz.auth.utils.RefererCsrfChecker;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.utils.JsonUtils;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 防止CSRF跨站请求攻击。<br>
 * 这个主要是防止外链连入到本系统。
 *
 * @author lightning
 */
public class RefererCsrfFilter extends RefererCsrfChecker implements Filter {


    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        //判断是否外链。
        String referer = req.getHeader("Referer");
        String serverName = request.getServerName();
        //请求不是来自本网站。
        if (null != referer && referer.indexOf(serverName) < 0) {
            //是否包含当前URL
            boolean isIngoreUrl = this.isIngores(referer);
            if (isIngoreUrl) {
                chain.doFilter(request, response);
            } else {
                String msg = String.format("系统不支持当前域名的访问，请联系管理员！<br> 服务器：%s,当前域名:%s", serverName, referer);
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                ServletUtil.write((HttpServletResponse) response, JsonUtils.toJSONString(ApiResponse.fail(GlobalApiCodes.INTERNAL_ERROR.getCode(), msg)), MediaType.APPLICATION_JSON_VALUE);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

}
