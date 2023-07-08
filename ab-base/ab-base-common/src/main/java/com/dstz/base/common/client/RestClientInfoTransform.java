package com.dstz.base.common.client;

import java.util.Collections;
import java.util.Map;

/**
 * rest client 请求信息交换，用于透传token及信息
 *
 * @author wacxhs
 */
public interface RestClientInfoTransform {

	/**
	 * 获取Cookie
	 *
	 * @return cookie
	 */
	default Map<String, String> getCookies() {
		return Collections.emptyMap();
	}

	/**
	 * 获取头信息
	 *
	 * @return 头信息
	 */
	default Map<String, String> getHeaders() {
		return Collections.emptyMap();
	}

}
