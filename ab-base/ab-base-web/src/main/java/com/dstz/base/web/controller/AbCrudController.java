package com.dstz.base.web.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.AbRequestUtils;
import com.dstz.base.entity.IPersistentEntity;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 增删改查控制器
 *
 * @param <T>
 * @author wacxhs
 * @since 2022-01-24
 */
public abstract class AbCrudController<T extends IPersistentEntity> extends AbBaseController {

    @Autowired
    protected AbBaseManager<T> abBaseManager;

    /**
     * 获取实体描述
     *
     * @return 实体描述
     */
    protected abstract String getEntityDesc();
    
    
    /**
     * 获取准入的过滤方法
     * @return
     */
    public Set<String> getAccessQueryFilters() {
		return null;
	}

	/**
     * 分页列表
     */
    @RequestMapping(value = "listJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> listJson(@Valid @RequestBody QueryParamDTO queryParamDto){
    	PageListDTO<T> pageList = abBaseManager.query(new DefaultAbQueryFilter(queryParamDto,getAccessQueryFilters()));
    	return ApiResponse.success(pageList);
    }

    /**
     * 按实体ID获取实体记录
     *
     * @param id 实体ID
     * @return 接口响应-实体记录
     */
    @RequestMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<T> get(@RequestParam(name = "id") String id) {
        T entity = null;
        if (StringUtils.hasLength(id)) {
            entity = abBaseManager.getById(id);
        }
        return ApiResponse.success(entity);
    }

    /**
     * 保存实体数据
     *
     * @param entity 实体
     * @return 接口响应-实体ID
     */
    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> save(@Valid @RequestBody T entity) {
        invokeBeforeCheck("saveCheck", entity);
        String desc;
        if (StringUtils.hasLength(entity.getId())) {
            desc = "更新%s成功";
            Assert.isTrue(abBaseManager.update(entity) > 0, () -> new BusinessMessage(GlobalApiCodes.DATA_VERSION_OLD));
        } else {
            desc = "添加%s成功";
            abBaseManager.create(entity);
        }
        return ApiResponse.success(entity.getId()).withMessage(String.format(desc, getEntityDesc()));
    }

    /**
     * 执行前检查
     *
     * @param methodName 方法名称
     * @param param      方法参数
     */
    protected void invokeBeforeCheck(String methodName, Object param) {
        Method saveCheck = ReflectUtil.getMethodByName(getClass(), methodName);
        if (saveCheck != null) {
            ReflectUtil.invoke(this, saveCheck, param);
        }
    }

    /**
     * 实体批量删除
     *
     * @param id 实体ID，多个,分隔
     * @return 接口响应
     */
    @RequestMapping(value = "remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> remove(@RequestParam(name = "id") String id) {
    	checkIsDemoEnvironment();
        List<String> ids = StrUtil.split(id, CharUtil.COMMA);
        invokeBeforeCheck("removeCheck", ids);
        abBaseManager.removeByIds(ids);
        final String message = String.format("删除%s成功", getEntityDesc());
        return ApiResponse.<String>success().withMessage(message);
    }
    
    // 演示环境禁止删除操作
    protected void checkIsDemoEnvironment() {
    	if("demoa5.tongzhouyun.com".equals(AbRequestUtils.getHttpServletRequest().getServerName())) {
        	 throw new BusinessException(GlobalApiCodes.REMOTE_CALL_ERROR.formatDefaultMessage("演示环境禁止删除操作，访问信息已经被统计！"));
        }
    }
    
}
