package com.dstz.sys.core.manager.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.events.AbRequestLogEvent;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.requestlog.AbRequestLog;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.sys.core.entity.SysLogErr;
import com.dstz.sys.core.manager.SysLogErrManager;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统异常日志 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Service("sysLogErrManager")
public class SysLogErrManagerImpl extends AbBaseManagerImpl<SysLogErr> implements SysLogErrManager {

    /**
     * 异常日志只允许更改状态,其他字段只能回显,禁止修改
     * @param entity 实体
     * @return
     */
    @Override
    public int update(SysLogErr entity) {
        return update(null, Wrappers.lambdaUpdate(SysLogErr.class)
                .eq(SysLogErr::getId, entity.getId())
                .set(SysLogErr::getStatus, entity.getStatus())
                .set(SysLogErr::getIpAddress,entity.getIpAddress()));
    }

    @EventListener(AbRequestLogEvent.class)
    public void abRequestLogEventListener(AbRequestLogEvent abRequestLogEvent){
        if (!AbRequestLogEvent.EventType.POST_PROCESS.equals(abRequestLogEvent.getEventType())) {
            return;
        }
        AbRequestLog requestLog = abRequestLogEvent.getRequestLog();
        Throwable exception = requestLog.getException();
        // 跳过提示信息异常
        if(exception == null || exception instanceof BusinessMessage){
            return;
        }
        ThreadUtil.execute(() -> {
            SysLogErr sysLogErr = new SysLogErr();
            sysLogErr.setAccount(requestLog.getUsername());
            sysLogErr.setIp(requestLog.getClientIp());
            sysLogErr.setIpAddress(StrUtil.EMPTY);
            sysLogErr.setStatus("unchecked");
            sysLogErr.setUrl(requestLog.getUrl());
            sysLogErr.setContent(ExceptionUtil.getRootCauseMessage(exception));
            sysLogErr.setHeads(JsonUtils.toJSONString(requestLog.getRequestHeaderMap()));

            Map<String, Object> paramMap = new HashMap<>(requestLog.getRequestParameterMap());
            paramMap.put("@RequestBody", requestLog.getRequestBody());

            sysLogErr.setRequestParam(JsonUtils.toJSONString(paramMap));
            sysLogErr.setStackTrace(ExceptionUtil.stacktraceToString(requestLog.getException(), -1));
            getBaseMapper().insert(sysLogErr);
        });
    }
}
