package com.dstz.base.common.map;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapProxy;
import cn.hutool.core.util.ReflectUtil;
import com.dstz.base.common.utils.CastUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 获取属性MapProxy，用于代理 getAttribute(String key, Class&lt;T&gt; toClass);
 *
 * @author wacxhs
 */
public class GetAttributeMapProxy extends MapProxy {

	private static final long serialVersionUID = -7468987711284643178L;

	private final Function<Map<String, Object>, Object> loadFunc;

	private volatile boolean lazyLoaded;

	public GetAttributeMapProxy(Map<?, ?> map) {
		super(map);
		this.lazyLoaded = true;
		this.loadFunc = null;
	}

	/**
	 * 构造
	 *
	 * @param map 被代理的Map
	 */
	public GetAttributeMapProxy(Map<?, ?> map, Function<Map<String, Object>, Object> loadFunc) {
		super(map);
		this.loadFunc = loadFunc;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		// 兼容Map
		if (Map.class.isAssignableFrom(method.getDeclaringClass())) {
			doLazyLoad();
			return ReflectUtil.invoke(this, method, args);
		}

		Object value;

		// 获取属性方法
		if ("getAttrValue".equals(method.getName()) && method.getParameterCount() == 2) {
			value = Convert.convert(CastUtils.<Class<?>>cast(args[1]), get(args[0]));
		} else {
			value = super.invoke(proxy, method, args);
		}

		if (value == null && !lazyLoaded) {
			doLazyLoad();
			// 加载完之后重新再获取一次值
			value = invoke(proxy, method, args);
		}

		return value;
	}

	private synchronized void doLazyLoad() {
		if (lazyLoaded) {
			return;
		}
		lazyLoaded = true;
		Object value = loadFunc.apply(CastUtils.cast(this));
		if (Objects.nonNull(value)) {
			if (value instanceof Map) {
				super.putAll(CastUtils.cast(value));
			} else {
				throw new UnsupportedOperationException("Lazy loading function only supports return value Map");
			}
		}
	}
}
