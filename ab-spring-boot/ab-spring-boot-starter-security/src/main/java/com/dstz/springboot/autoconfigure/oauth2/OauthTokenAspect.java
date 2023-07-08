package com.dstz.springboot.autoconfigure.oauth2;

import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.constant.AuthConstant;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.exceptions.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义切面，拦截oauth2接口处理返回参数
 *
 * @author lightning
 */
@Aspect
@Component
public class OauthTokenAspect {
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Principal principal = (Principal) args[0];
        if (!(principal instanceof Authentication)) {
            throw new ApiException(AuthStatusCode.NO_CLIENT_AUTHENTICATION);
        }
        Map<String, String> parameters = (Map<String, String>) args[1];
        String grantType = parameters.get(AuthConstant.GRANT_TYPE);


        Object proceed = joinPoint.proceed();
        //不处理 authorization_code 模式
        if (AuthConstant.AUTHORIZATION_CODE.equals(grantType)) {
            return proceed;
        } else {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success(body));
        }

    }

}
