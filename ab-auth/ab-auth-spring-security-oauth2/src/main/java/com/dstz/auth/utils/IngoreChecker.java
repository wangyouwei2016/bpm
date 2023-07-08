package com.dstz.auth.utils;

import com.dstz.base.common.constats.StrPool;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 忽略鉴权地址检查
 *
 * @author lightning
 * @since 2022-02-07
 */
public class IngoreChecker {

    /**
     * 忽略地址
     */
    private String[] ignoreUrls;

    public void setIgnoreUrls(String[] ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    /**
     * 判断当前URL是否在忽略的地址中。
     *
     * @param request 请求
     * @return
     */
    public boolean isIngores(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        // 会再跳转 index.html 所以直接忽略
        return StrPool.SLASH.equals(servletPath) || PatternMatchUtils.simpleMatch(ignoreUrls, servletPath);
    }

}
