package com.dstz.sys.rest.controller;


import cn.hutool.core.collection.CollUtil;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.groovy.script.api.IGroovyScriptEngine;
import com.dstz.sys.core.entity.SysScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;

/**
 * <p>
 * 常用脚本 前端控制器
 * </p>
 *
 * @author wacxhs
 * @since 2022-01-25
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/script")
public class SysScriptController extends AbCrudController<SysScript> {

    private static final Logger logger = LoggerFactory.getLogger(SysScriptController.class);

    @Autowired
    private IGroovyScriptEngine groovyScriptEngine;

    @Override
    protected String getEntityDesc() {
        return "常用脚本";
    }
    
    /**
     * 允许 name，alias 作为入参传入列表页
     */
    private Set<String> accessQueryFilters = CollUtil.newHashSet("name","typeCode");
    
    @Override
	public Set<String> getAccessQueryFilters() {
		return accessQueryFilters;
	}

    /**
     * 执行脚本
     *
     * @param name   脚本名称
     * @param script 脚本
     * @return 接口响应-执行结果
     */
    @RequestMapping(value = "executeScript", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Object> executeScript(@RequestParam("key") String name, @RequestParam("script") String script) {
    	checkIsDemoEnvironment();
        Object retVal;
        try {
            // 执行结果
            retVal = groovyScriptEngine.evaluate(script, new HashMap<>(0));
        } catch (Exception e) {
            logger.warn("执行脚本出错, 脚本名称：{}, 脚本: {}", name, script, e);
            retVal = String.format("执行脚本出错, %s", e.getMessage());
        }
        return ApiResponse.success(retVal);
    }
}
