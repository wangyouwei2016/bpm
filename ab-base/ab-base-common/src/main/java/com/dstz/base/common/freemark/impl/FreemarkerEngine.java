package com.dstz.base.common.freemark.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.freemark.IFreemarkScript;
import com.dstz.base.common.freemark.IFreemarkerEngine;
import com.dstz.base.common.property.PropertyEnum;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <pre>
 * Freemarker接口 定义
 * 作者:lightning
 * 日期:2022-01-02
 * 版权: 深圳市大世同舟信息科技有限公司
 * </pre>
 */
@Component
public class FreemarkerEngine implements IFreemarkerEngine {
    private Configuration formTemplateConfig;
    protected Logger LOG = LoggerFactory.getLogger(getClass());

    public Configuration getFormTemplateConfiguration() {
        try {
            if (formTemplateConfig == null) {
                String templatePath = PropertyEnum.FORM_TEMPLATE_URL.getPropertyValue(String.class);
                formTemplateConfig = new Configuration();
                formTemplateConfig.setDefaultEncoding("UTF-8");
                formTemplateConfig.setDirectoryForTemplateLoading(new File(templatePath));
            }
            return formTemplateConfig;
        } catch (Exception e) {
            throw new BusinessException(GlobalApiCodes.INTERNAL_ERROR, e);
        }

    }

    @Override
    public String parseByString(String templateSource, Object model) {
        if (model != null && model instanceof Map) {
            //将所有表单生成器的实现类注入到模板引擎中
            Map<String, IFreemarkScript> scirptImpls = SpringUtil.getBeansOfType(IFreemarkScript.class);
            for (Entry<String, IFreemarkScript> scriptMap : scirptImpls.entrySet()) {
                ((Map) model).put(scriptMap.getKey(), scriptMap.getValue());
            }
        }

        try {
            Configuration cfg = new Configuration();
            StringTemplateLoader loader = new StringTemplateLoader();
            cfg.setTemplateLoader(loader);
            cfg.setClassicCompatible(true);
            loader.putTemplate("freemaker", templateSource);
            Template template = cfg.getTemplate("freemaker");
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            LOG.error(String.format("freemaker模板【%s】解析失败：%s", templateSource, e.getMessage()));
            throw new BusinessException(GlobalApiCodes.INTERNAL_ERROR.formatMessage("模板解析失败,可能原因为：{}", e.getMessage()), e);
        }
    }

}
