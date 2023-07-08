
package com.dstz.springboot.autoconfigure.oauth2;

import com.dstz.auth.authentication.AbClientDetailsServiceImpl;
import com.dstz.auth.authentication.AbDaoAuthenticationProvider;
import com.dstz.auth.constant.AuthConstant;
import com.dstz.auth.exception.Auth2CustomizeExceptionTranslator;
import com.dstz.auth.login.CustomPwdEncoder;
import com.dstz.auth.login.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Collections;

import static com.dstz.auth.authentication.api.constant.AuthApiConstant.*;


/**
 * 颁发令牌配置
 *
 * @author lightning
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService clientDetailsService;


    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;



    @Autowired
    private TokenStore tokenStore;

    CustomPwdEncoder customPwdEncoder = new CustomPwdEncoder();

    @Bean
    public AbDaoAuthenticationProvider abDaoAuthenticationProvider() {
        AbDaoAuthenticationProvider abDaoAuthenticationProvider = new AbDaoAuthenticationProvider();
        abDaoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        abDaoAuthenticationProvider.setUserDetailsService(userDetailsService());
        abDaoAuthenticationProvider.setPasswordEncoder(customPwdEncoder);
        return abDaoAuthenticationProvider;
    }

    @Bean("userDetailsService")
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() {
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(abDaoAuthenticationProvider()));
        providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationContext));
        return providerManager;
    }


    /**
     * 配置令牌端点安全约束
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)  {
        security.tokenKeyAccess(TOKEN_SERVER_SECURITY_CONFIGURER)
                .checkTokenAccess(TOKEN_SERVER_SECURITY_CONFIGURER)
                .allowFormAuthenticationForClients();
    }


    /**
     * 配置客户端详情服务
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        AbClientDetailsServiceImpl clientDetailsService = new AbClientDetailsServiceImpl(dataSource);
        clientDetailsService.setSelectClientDetailsSql(AuthConstant.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(AuthConstant.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }


    /**
     * 配置令牌访问端点，令牌服务
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints
                //定制授权同意页面
                .pathMapping(TOKEN_SERVER_AUTH_DEFAULTPATH, TOKEN_SERVER_AUTH_CUSTOMPATH)
                //认证管理器
                .authenticationManager(authenticationManagerBean())
                //自定义异常处理
                //.exceptionTranslator(new Auth2CustomizeExceptionTranslator())
                //密码模式的用户信息管理
                .userDetailsService(userDetailsService())
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices())
                //令牌管理服务
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);
    }


    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        //客户端详情服务
        service.setClientDetailsService(clientDetailsService);
        // 是否支持令牌刷新
        service.setSupportRefreshToken(true);
        // 令牌存储策略
        service.setTokenStore(tokenStore);
        //令牌增强
       /* TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);*/
        // 令牌默认有效期2小时
        service.setAccessTokenValiditySeconds(ACCESSTOKEN_VALIDITY_SECONDS);
        // 刷新令牌默认有效期3 天
        service.setRefreshTokenValiditySeconds(REFRESHTOKEN_VALIDITY_SECONDS);
        return service;
    }
}

