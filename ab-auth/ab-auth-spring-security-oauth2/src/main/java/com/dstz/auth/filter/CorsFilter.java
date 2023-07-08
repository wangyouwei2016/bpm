package com.dstz.auth.filter;

import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cors过滤器
 *
 * @author lightning
 */
public class CorsFilter extends OncePerRequestFilter implements Ordered {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        //此处我们忽略跨域问题由 @RefererCsrfFilter.java 来处理
        //默认全部支持跨域
        setHeader(resp, HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "accept, origin, content-type, accessToken, token, Authorization, Cookie");
        setHeader(resp, HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Ab-Product-Past");
        setHeader(resp, HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,PUT");
        setHeader(resp, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
        setHeader(resp, HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());

        if (req.getMethod().equals(HttpMethod.OPTIONS.name())) {
            resp.setStatus(HttpStatus.OK.value());
            setHeader(resp, HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    private void setHeader(HttpServletResponse resp, String name, String value) {
        if (resp.getHeader(name) == null) {
            resp.setHeader(name, value);
        }
    }

    @Override
    public int getOrder() {
        return -300;
    }
}

