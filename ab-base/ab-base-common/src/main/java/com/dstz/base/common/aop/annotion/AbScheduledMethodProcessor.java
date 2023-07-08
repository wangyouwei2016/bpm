package com.dstz.base.common.aop.annotion;

import cn.hutool.core.date.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 全局定时器拦截AOP处理，详见http://www.agilebpm.cn/guide/system-setting/crontab.html
 * 作者:wacxhs
 * 邮箱:wacxhs@agilebpm.cn
 * 日期:2021-11-13
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
public class AbScheduledMethodProcessor {

	private static final Logger logger = LoggerFactory.getLogger(AbScheduledMethodProcessor.class);


	public static List<AbScheduleDefinition> process(ConfigurableListableBeanFactory beanFactory) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.debug("开始解析@AbScheduled方法标记注解");
		List<AbScheduleDefinition> definitions = new ArrayList<>();
		String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
		for (String beanName : beanNames) {
			if (!ScopedProxyUtils.isScopedTarget(beanName)) {
				Class<?> type = null;
				try {
					type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
				} catch (Throwable ex) {
					// An unresolvable bean type, probably from a lazy bean - let's ignore it.
					if (logger.isDebugEnabled()) {
						logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
					}
				}
				if (type != null) {
					if (ScopedObject.class.isAssignableFrom(type)) {
						try {
							Class<?> targetClass = AutoProxyUtils.determineTargetClass(
									beanFactory, ScopedProxyUtils.getTargetBeanName(beanName));
							if (targetClass != null) {
								type = targetClass;
							}
						} catch (Throwable ex) {
							// An invalid scoped proxy arrangement - let's ignore it.
							if (logger.isDebugEnabled()) {
								logger.debug("Could not resolve target bean for scoped proxy '" + beanName + "'", ex);
							}
						}
					}
					try {
						processBean(definitions, beanName, type);
					} catch (Throwable ex) {
						throw new BeanInitializationException("Failed to process @EventListener " +
								"annotation on bean with name '" + beanName + "'", ex);
					}
				}
			}
		}

		stopWatch.stop();
		logger.debug("解析@AbScheduled方法标记注解结束, {}", stopWatch.shortSummary(TimeUnit.MILLISECONDS));
		return definitions;
	}

	private static void processBean(List<AbScheduleDefinition> definitions, String beanName, Class<?> type) {
		try {
			Map<Method, AbScheduled> annotatedMethods = MethodIntrospector.selectMethods(type, (MethodIntrospector.MetadataLookup<AbScheduled>) method -> AnnotatedElementUtils.findMergedAnnotation(method, AbScheduled.class));
			if (!CollectionUtils.isEmpty(annotatedMethods)) {
				for (Map.Entry<Method, AbScheduled> entry : annotatedMethods.entrySet()) {
					AbScheduled abScheduled = entry.getValue();
					definitions.add(new AbScheduleDefinition(abScheduled.jobKey(), beanName, entry.getKey()));
				}
			}
		} catch (Throwable ex) {
			// An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
			if (logger.isDebugEnabled()) {
				logger.debug("Could not resolve methods for bean with name '" + beanName + "'", ex);
			}
		}
	}

	public static final class AbScheduleDefinition {

		private final String jonKey;

		private final String beanName;

		private final Method method;

		public AbScheduleDefinition(String jonKey, String beanName, Method method) {
			this.jonKey = jonKey;
			this.beanName = beanName;
			this.method = method;
		}

		public String getJonKey() {
			return jonKey;
		}

		public String getBeanName() {
			return beanName;
		}

		public Method getMethod() {
			return method;
		}
	}
}
