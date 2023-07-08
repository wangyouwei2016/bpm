package com.dstz.groovy.script.api;

/**
 * groovy脚本运行异常
 *
 * @author wacxhs
 * @since 2022-01-26
 */
public class GroovyEngineEvaluateException extends RuntimeException {

    private static final long serialVersionUID = 1704863836362954551L;

    /**
     * 执行脚本
     */
    private final String script;

    public GroovyEngineEvaluateException(String message, String script) {
        super(message);
        this.script = script;
    }

    public GroovyEngineEvaluateException(String message, Throwable cause, String script) {
        super(message, cause);
        this.script = script;
    }

    /**
     * 运行脚本
     *
     * @return 脚本
     */
    public String getScript() {
        return script;
    }
}
