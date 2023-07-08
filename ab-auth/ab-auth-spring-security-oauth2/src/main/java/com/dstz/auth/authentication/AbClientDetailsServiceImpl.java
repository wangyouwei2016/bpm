package com.dstz.auth.authentication;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.dstz.auth.authentication.api.SysApplicationApi;
import com.dstz.auth.authentication.api.constant.AuthStatusCode;
import com.dstz.auth.authentication.vo.SysApplicationVO;
import com.dstz.auth.exception.Auth2CustomizeExceptionTranslator;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * 客户端信息
 *
 * @author lightning
 */
@Component
public class AbClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Autowired
    private SysApplicationApi sysApplicationApi;

    public AbClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        try {
            SysApplicationVO sysApplication = sysApplicationApi.getByCode(clientId);
            Assert.notNull(sysApplication, () -> new BusinessException(AuthStatusCode.NO_FIND_APP.formatDefaultMessage(clientId)));

            BaseClientDetails baseClientDetails = new BaseClientDetails();
            //客户端clientId
            baseClientDetails.setClientId(sysApplication.getCode());
            //客户端秘钥
            baseClientDetails.setClientSecret("{noop}" + sysApplication.getSecret());
            // 方法级权限控制 指定客户端申请的权限范围
            baseClientDetails.setScope(StrUtil.split(sysApplication.getScope(), StrPool.COMMA));
            //客户端所能访问的资源id集合,多个资源时用逗号(,)分隔 如果没设置，就是所有权限
            baseClientDetails.setResourceIds(StrUtil.isNotEmpty(sysApplication.getResourceIds()) ? StrUtil.split(sysApplication.getResourceIds(), StrPool.COMMA) : null);
            //支持的鉴权类型
            baseClientDetails.setAuthorizedGrantTypes(StrUtil.isNotEmpty(sysApplication.getGrantTypes()) ? StrUtil.split(sysApplication.getGrantTypes(), StrPool.COMMA) : CollUtil.newArrayList());

            //回调地址
            baseClientDetails.setRegisteredRedirectUri(StrUtil.isNotEmpty(sysApplication.getRedirectUri()) ? CollUtil.newHashSet(StrUtil.split(sysApplication.getRedirectUri(), StrPool.COMMA)) : null);

            //设置用户是否自动Approval操作, 默认值为 'false'该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权.
            baseClientDetails.setAutoApproveScopes(Collections.singletonList(sysApplication.getAutoapprove().toString()));

            //token有效时间
            baseClientDetails.setAccessTokenValiditySeconds(sysApplication.getAccessTokenValidity());
            //刷新token有效时间
            baseClientDetails.setRefreshTokenValiditySeconds(sysApplication.getRefreshTokenValidity());
            return baseClientDetails;
        } catch (Exception e) {
            throw new Auth2CustomizeExceptionTranslator.CustomizeException(AuthStatusCode.LOAD_CLIENT_BY_CLIENTID_ERROR.formatDefaultMessage(e.getMessage()));
        }
    }
}
