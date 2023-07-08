package com.dstz.base.common.client;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.constats.StrPool;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * rest 请求信息转发组合
 *
 * @author wacxhs
 */
public class RestClientInfoTransformComposite implements RestClientInfoTransform {

	private final Collection<RestClientInfoTransform> transforms;

	public RestClientInfoTransformComposite(Collection<RestClientInfoTransform> transforms) {
		this.transforms = transforms;
	}

	@Override
	public Map<String, String> getCookies() {
		Map<String, String> mergeCookies = MapUtil.newHashMap();
		for (RestClientInfoTransform transform : transforms) {
			Map<String, String> cookies = transform.getCookies();
			if (MapUtil.isNotEmpty(cookies)) {
				mergeCookies.putAll(cookies);
			}
		}
		return mergeCookies;
	}

	@Override
	public Map<String, String> getHeaders() {
		Map<String, String> mergeHeaders = MapUtil.newHashMap();
		for (RestClientInfoTransform transform : transforms) {
			Map<String, String> headers = transform.getHeaders();
			if (MapUtil.isNotEmpty(headers)) {
				mergeHeaders.putAll(headers);
			}
		}
		return mergeHeaders;
	}

	/**
	 * 获取cookie string
	 *
	 * @return cookie string
	 */
	public String getCookiesString() {
		return getCookies().entrySet().stream().map(o -> StrUtil.join("=", o.getKey(), o.getValue())).collect(Collectors.joining(StrPool.SEMICOLON));
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static RestClientInfoTransformComposite getInstance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		static final RestClientInfoTransformComposite INSTANCE = new RestClientInfoTransformComposite(SpringUtil.getBeansOfType(RestClientInfoTransform.class).values());
	}
}
