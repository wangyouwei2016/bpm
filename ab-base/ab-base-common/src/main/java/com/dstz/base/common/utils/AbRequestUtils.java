package com.dstz.base.common.utils;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 请求工具
 *
 * @author wacxhs
 */
public class AbRequestUtils {

	private AbRequestUtils() throws IllegalAccessException {
		throw new IllegalAccessException();
	}

	/**
	 * 获取 HttpServletRequest
	 *
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * 获取 HttpServletResponse
	 *
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getHttpServletResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return ((ServletRequestAttributes) requestAttributes).getResponse();
	}

	/**
	 * 获取当前ip
	 * @return ip
	 */
	public static String getRequestIp() {
		String clientIp = ServletUtil.getClientIP(getHttpServletRequest());
		return "0:0:0:0:0:0:0:1".equals(clientIp) ? "127.0.0.1" : clientIp;
	}

}
