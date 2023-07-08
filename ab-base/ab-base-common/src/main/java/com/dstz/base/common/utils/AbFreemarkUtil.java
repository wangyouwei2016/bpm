package com.dstz.base.common.utils;

import java.io.IOException;

import com.dstz.base.common.freemark.IFreemarkerEngine;
import com.dstz.base.common.freemark.impl.FreemarkerEngine;

import cn.hutool.extra.spring.SpringUtil;
import freemarker.template.TemplateException;

/**
 * Freemarker工具类
 *
 * @author lightning
 */
public class AbFreemarkUtil {

    private static volatile IFreemarkerEngine freemarkerEngine;

    public static IFreemarkerEngine getFreemarkerEngine() {
        if (freemarkerEngine == null) {
            synchronized (AbFreemarkUtil.class) {
                freemarkerEngine = SpringUtil.getBean(FreemarkerEngine.class);
            }
        }
        return freemarkerEngine;
    }

    /**
     * 根据字符串模版解析出内容
     *
     * @param templateSource 字符串模版。
     * @param model          环境参数。
     * @return 解析后的文本
     * @throws TemplateException
     * @throws IOException
     */
    public static String parseByString(String templateSource, Object model) {
        return getFreemarkerEngine().parseByString(templateSource, model);
    }

}

