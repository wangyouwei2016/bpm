package com.dstz.groovy.script.api;

import java.util.Map;

/**
 * Groovy脚本引擎
 *
 * @author wacxhs
 * @since 2022-01-26
 */
public interface IGroovyScriptEngine {
    /**
     * 运行脚本
     *
     * @param script 脚本
     * @param vars   变量
     * @return 运行结果
     */
    <T> T evaluate(String script, Map<String, Object> vars);
}
