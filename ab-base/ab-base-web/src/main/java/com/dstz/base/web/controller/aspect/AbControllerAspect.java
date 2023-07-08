package com.dstz.base.web.controller.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.MDCConstant;
import com.dstz.base.common.events.AbRequestLogEvent;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.requestlog.AbRequestLog;
import com.dstz.base.common.utils.AbRequestUtils;
import com.dstz.base.common.utils.CastUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 请求控制器AOP切面
 *
 * @author wacxhs
 */
public class AbControllerAspect implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AbControllerAspect.class);

	/**
	 * 获取当前用户
	 *
	 * @return 当前用户
	 */
	private Optional<IUser> getCurrentUser() {
		try {
			return UserContextUtils.getUser();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	/**
	 * 获取当前用户所在的当前组织
	 *
	 * @return 当前组织
	 */
	private Optional<IGroup> getCurrentGroup() {
		try {
			return UserContextUtils.getGroup();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Nullable
	@Override
	public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
		final HttpServletRequest request = Objects.requireNonNull(AbRequestUtils.getHttpServletRequest());
		final Map<String, String> parameterMap = ServletUtil.getParamMap(request);
		final String traceId = getTraceId();

		HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
		// @PathVariable 获取
		Optional.ofNullable(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).filter(ObjectUtil::isNotEmpty).<Map<String, String>>map(CastUtils::cast).ifPresent(parameterMap::putAll);

		AbRequestLog.Builder requestLogBuilder = AbRequestLog.newBuilder();
		requestLogBuilder.withTraceId(traceId);
		getCurrentUser().ifPresent(u -> requestLogBuilder.withUserId(u.getUserId()).withUsername(u.getUsername()));
		getCurrentGroup().ifPresent(g->requestLogBuilder.withGroupId(g.getGroupId()).withGroupName(g.getGroupName()));
		requestLogBuilder.withClientIp(getClientIp(request));
		requestLogBuilder.withRequestMethod(request.getMethod()).withUrl(request.getRequestURL().toString());
		requestLogBuilder.withPathPattern((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
		requestLogBuilder.withRequestTime(new Date());
		requestLogBuilder.withRequestHeaderMap(ServletUtil.getHeaderMap(request)).withRequestParameterMap(parameterMap);
		requestLogBuilder.withRequestBody(Arrays.stream(handlerMethod.getMethodParameters()).filter(item -> item.hasParameterAnnotation(RequestBody.class)).map(MethodParameter::getParameterIndex).findFirst().map(parameterIndex -> invocation.getArguments()[parameterIndex]).orElse(null));
		// 放置到request请求中，供其他地方获取设值
		request.setAttribute(AbRequestLog.class.getName(), requestLogBuilder.build());

		SpringUtil.publishEvent(AbRequestLogEvent.createPreProcess(requestLogBuilder.build()));

		Throwable throwable = null;
		Object returnVal = null;
		try {
			returnVal = invocation.proceed();
		} catch (Throwable e) {
			throwable = e;
		}
		requestLogBuilder.withResponseTime(new Date());
		// 再重新获取下用户信息，用于登录后信息获取
		UserContextUtils.getUser().ifPresent(u -> requestLogBuilder.withUserId(u.getUserId()).withUsername(u.getUsername()));
		getCurrentGroup().ifPresent(g->requestLogBuilder.withGroupId(g.getGroupId()).withGroupName(g.getGroupName()));
		// 异常信息
		if (Objects.nonNull(throwable)) {
			Throwable causedBy = ExceptionUtil.getCausedBy(throwable, BusinessMessage.class);
			// 提示性消息不记录到exception里
			if(causedBy != null) {
				BusinessMessage businessMessage = (BusinessMessage) causedBy;
				requestLogBuilder.withResponseBody(ApiResponse.fail(businessMessage.getBaseCode().getCode(), businessMessage.getMessage()));
			}else{
				requestLogBuilder.withException(throwable);
			}
			SpringUtil.publishEvent(AbRequestLogEvent.createPostProcess(requestLogBuilder.build()));
			throw throwable;
		}
		// 正常信息返回
		if (returnVal instanceof ResponseEntity) {
			ResponseEntity<?> responseEntity = (ResponseEntity<?>) returnVal;
			if (!(responseEntity.getBody() instanceof byte[])) {
				requestLogBuilder.withResponseBody(responseEntity.getBody());
			}
		} else {
			requestLogBuilder.withResponseBody(returnVal);
		}
		SpringUtil.publishEvent(AbRequestLogEvent.createPostProcess(requestLogBuilder.build()));
		return returnVal;
	}

	private String getTraceId() {
		String traceId = MDC.get(MDCConstant.TRACE_ID);
		if (StringUtils.isNotEmpty(traceId)) {
			return traceId;
		}
		return IdUtil.simpleUUID();
	}


	private String getClientIp(HttpServletRequest request) {
		String clientIp = ServletUtil.getClientIP(request);
		return "0:0:0:0:0:0:0:1".equals(clientIp) ? "127.0.0.1" : clientIp;
	}

}
