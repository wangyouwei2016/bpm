package com.dstz.base.common.context;


/**
 * 上下文副本，用于多线程下的数据传递
 *
 * @author wacxhs
 */
public interface ContextDuplication {

    /**
     * 从Context制作出一个副本
     *
     * @return 副本
     */
    Object duplicate();

    /**
     * 复制出的副本填充当前Context
     *
     * @param duplicate 副本
     */
    void fill(Object duplicate);

}
