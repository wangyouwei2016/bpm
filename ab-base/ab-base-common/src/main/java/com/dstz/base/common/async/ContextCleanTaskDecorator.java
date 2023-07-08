package com.dstz.base.common.async;

import com.dstz.base.common.utils.ContextCleanUtils;
import org.springframework.core.task.TaskDecorator;

/**
 * 上下文清理装饰器
 *
 * @author wacxhs
 */
public class ContextCleanTaskDecorator implements TaskDecorator {

	public static final ContextCleanTaskDecorator INSTANCE = new ContextCleanTaskDecorator();

	@Override
	public Runnable decorate(Runnable runnable) {
		return () -> {
			ContextCleanUtils.executeAll();
			try {
				// 复制对象填充
				runnable.run();
			} finally {
				ContextCleanUtils.executeAll();
			}
		};
	}
}
