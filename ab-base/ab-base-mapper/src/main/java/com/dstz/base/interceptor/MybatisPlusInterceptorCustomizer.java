package com.dstz.base.interceptor;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

/**
 * 自定义处理器
 *
 * @author wacxhs
 */
public interface MybatisPlusInterceptorCustomizer {

	/**
	 * 自定义拦截处理
	 *
	 * @param mybatisPlusInterceptor mybatis拦截器
	 */
	void customize(MybatisPlusInterceptor mybatisPlusInterceptor);

}
