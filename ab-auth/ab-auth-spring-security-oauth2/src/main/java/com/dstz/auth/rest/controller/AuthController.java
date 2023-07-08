package com.dstz.auth.rest.controller;

import com.dstz.auth.login.AbLoginService;
import com.dstz.auth.login.AbTokenService;
import com.dstz.auth.login.model.RefreshTokenReqVO;
import com.dstz.auth.login.model.TokenReqVO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.dstz.auth.authentication.api.constant.AuthApiConstant.OAUTH_TOKEN_PASSWORD;

/**
 * 获取token
 *
 * @author lightning
 * @since 2023-01-30
 */
@RestController
@RequestMapping(AbAppRestConstant.AUTH_SERVICE_PREFIX)
public class AuthController {

    @Autowired
    AbTokenService abTokenService;

    @Autowired
    AbLoginService loginService;

    /**
     * 获取token接口
     *
     * @param tokenReqVO
     * @return
     */
    @RequestMapping("/getToken")
    public ApiResponse<OAuth2AccessToken> getToken(@Valid @RequestBody TokenReqVO tokenReqVO) {
        //获取token
        tokenReqVO.setGrantType(OAUTH_TOKEN_PASSWORD);
        ApiResponse<OAuth2AccessToken> result = abTokenService.getToken(tokenReqVO);
        //缓存数据
        loginService.loginPostposition(result);
        return result;
    }


    /**
     * 刷新token接口
     *
     * @param refreshTokenReqVO
     * @return
     */
    @RequestMapping("/refreshToken")
    public ApiResponse<OAuth2AccessToken> refreshToken(@Valid @RequestBody RefreshTokenReqVO refreshTokenReqVO) {
        //刷新token
        ApiResponse<OAuth2AccessToken> result = abTokenService.refreshToken(refreshTokenReqVO);
        //缓存数据
        loginService.loginPostposition(result);
        return result;
    }
}
