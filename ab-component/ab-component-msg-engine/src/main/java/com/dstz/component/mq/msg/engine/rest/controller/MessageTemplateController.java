package com.dstz.component.mq.msg.engine.rest.controller;


import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.cache.ICache;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.constats.NumberPool;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.component.mq.msg.engine.constants.MsgCackeKeyConstant;
import com.dstz.component.mq.msg.engine.core.entity.MessageTemplate;
import com.dstz.component.mq.msg.engine.core.manager.MessageTemplateManager;
import com.dstz.component.msg.api.MessageTemplateApi;
import com.dstz.component.msg.api.vo.MessageTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lightning
 * @since 2022-11-14
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/messageTemplate/")
public class MessageTemplateController extends AbCrudController<MessageTemplate> {

    @Autowired
    MessageTemplateManager messageTemplateManager;

    @Autowired
    ICache iCache;

    @Autowired
    MessageTemplateApi messageTemplateApi;

    @Override
    protected String getEntityDesc() {
        return "";
    }

    /**
     * 获取可用模板
     *
     * @return
     */
    @RequestMapping(value = "getEnabledTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List<MessageTemplate>> getEnabledTemplate() {
        return ApiResponse.success(messageTemplateManager.selectByWrapper(Wrappers.lambdaQuery(MessageTemplate.class).eq(MessageTemplate::getEnabled, NumberPool.BOOLEAN_TRUE)));
    }

    /**
     * 获取可用模板
     *
     * @return
     */
    @RequestMapping(value = "getTemplateByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MessageTemplateVO> getTemplateByCode(@RequestParam(name = "code") String code) {
        return ApiResponse.success(messageTemplateApi.getTemplateByCode(code));
    }

    /**
     * 保存实体数据
     *
     * @param entity 实体
     * @return 接口响应-实体ID
     */
    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> save(@Valid @RequestBody MessageTemplate entity) {
        String desc;
        if (StringUtils.hasLength(entity.getId())) {
            desc = "更新%s成功";
            messageTemplateManager.update(entity);
        } else {
            desc = "添加%s成功";
            messageTemplateManager.create(entity);
        }
        iCache.invalidate(AbCacheRegionConstant.MSG_REGION, MsgCackeKeyConstant.GET_MESSAGE_TEMPLATE_LIST + entity.getCode());
        return ApiResponse.success(entity.getId()).withMessage(String.format(desc, getEntityDesc()));
    }

    /**
     * 实体批量删除
     *
     * @param id 实体ID，多个,分隔
     * @return 接口响应
     */
    @RequestMapping(value = "remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> remove(@RequestParam(name = "id") String id) {
        List<String> ids = StrUtil.split(id, CharUtil.COMMA);
        List<MessageTemplate> messageTemplateList = messageTemplateManager.selectByIds(ids);
        messageTemplateList.forEach(e -> {
            iCache.invalidate(AbCacheRegionConstant.MSG_REGION, MsgCackeKeyConstant.GET_MESSAGE_TEMPLATE_LIST + e.getCode());
        });
        invokeBeforeCheck("removeCheck", ids);
        abBaseManager.removeByIds(ids);
        final String message = String.format("删除%s成功", getEntityDesc());
        return ApiResponse.<String>success().withMessage(message);
    }


}
