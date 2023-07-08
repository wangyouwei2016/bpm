package com.dstz.base.common.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.dstz.base.common.utils.AbRequestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 配置式客户端请求信息转发
 *
 * @author wacxhs
 */
public class ConfigRestClientInfoTransform implements RestClientInfoTransform {

	private final List<Pair<String, String>> cookies;
	private final List<Pair<String, String>> headers;

	public ConfigRestClientInfoTransform(List<Pair<String, String>> cookies, List<Pair<String, String>> headers) {
		this.cookies = cookies;
		this.headers = headers;
	}

	@Override
	public Map<String, String> getCookies() {
		if (CollUtil.isEmpty(cookies)) {
			return Collections.emptyMap();
		}
		Map<String, Cookie> cookieMap = Optional.ofNullable(AbRequestUtils.getHttpServletRequest()).map(ServletUtil::readCookieMap).orElse(Collections.emptyMap());
		Map<String, String> returnCookieMap = MapUtil.newHashMap(cookies.size());
		Cookie cookie;
		for (Pair<String, String> cookieDefinition : cookies) {
			if (StrUtil.isNotEmpty(cookieDefinition.getValue())) {
				returnCookieMap.put(cookieDefinition.getKey(), cookieDefinition.getValue());
			} else if (Objects.nonNull(cookie = cookieMap.get(cookieDefinition.getKey()))) {
				returnCookieMap.put(cookieDefinition.getKey(), cookie.getValue());
			}
		}
		return returnCookieMap;
	}

	@Override
	public Map<String, String> getHeaders() {
		if (CollUtil.isEmpty(headers)) {
			return Collections.emptyMap();
		}
		HttpServletRequest request = AbRequestUtils.getHttpServletRequest();
		Map<String, String> returnHeaderMap = MapUtil.newHashMap(headers.size());
		for (Pair<String, String> headerDefinition : headers) {
			if (StrUtil.isNotEmpty(headerDefinition.getValue())) {
				returnHeaderMap.put(headerDefinition.getKey(), headerDefinition.getValue());
			} else if (request != null) {
				returnHeaderMap.put(headerDefinition.getKey(), request.getHeader(headerDefinition.getKey()));
			}
		}
		return returnHeaderMap;
	}
}
