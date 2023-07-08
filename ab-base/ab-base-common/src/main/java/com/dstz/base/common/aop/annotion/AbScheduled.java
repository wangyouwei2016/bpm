package com.dstz.base.common.aop.annotion;

import java.lang.annotation.*;

/**
 * ab 调度
 *
 * @author wacxhs
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface AbScheduled {

	/**
	 * 调度任务KEY，也可用于其它中间件
	 *
	 * @return 调度任务KEY
	 */
	String jobKey();
}
