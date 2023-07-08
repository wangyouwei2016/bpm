package com.dstz.base.common.async;

import com.dstz.base.common.utils.ContextDuplicationUtils;
import org.springframework.core.task.TaskDecorator;

/**
 * 上下文传递装饰器
 *
 * @author wacxhs
 */
public class ContextDuplicationTaskDecorator implements TaskDecorator {

	public static final ContextDuplicationTaskDecorator INSTANCE = new ContextDuplicationTaskDecorator();

	@Override
	public Runnable decorate(Runnable target) {
		final Object[] duplicateObjects = ContextDuplicationUtils.duplicateObjects();
		return () -> {
			ContextDuplicationUtils.fillDuplicateObjects(duplicateObjects);
			target.run();
		};
	}
}
