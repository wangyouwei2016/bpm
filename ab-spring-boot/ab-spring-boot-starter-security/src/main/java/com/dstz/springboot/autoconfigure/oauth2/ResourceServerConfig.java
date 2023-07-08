package com.dstz.springboot.autoconfigure.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.auth.authentication.AbResourceRoleAuthProvider;
import com.dstz.auth.authentication.AbResourceRoleRelationProvider;
import com.dstz.auth.exception.AuthExceptionEntryPoint;
import com.dstz.auth.exception.CustomizeAccessDeniedHandler;
import com.dstz.auth.filter.*;
import com.dstz.auth.forbidden.DefaultAccessDeniedHandler;
import com.dstz.auth.login.logout.DefualtLogoutSuccessHandler;
import com.dstz.base.common.constats.StrPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * 资源验证配置
 *
 * @author lightning
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties({AbSecurityProperties.class})
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements WebSecurityCustomizer {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AbSecurityProperties abSecurityProperties;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 实现匿名鉴权
        OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler = new OAuth2WebSecurityExpressionHandler();
        oAuth2WebSecurityExpressionHandler.setTrustResolver(authenticationTrustResolver());
        resources.expressionHandler(oAuth2WebSecurityExpressionHandler);
        
        resources.tokenStore(tokenStore).stateless(true);
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
                 .accessDeniedHandler(new CustomizeAccessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (Boolean.TRUE.equals(abSecurityProperties.getEnableCsrf())) {
            //防止外链连入到本系统。
            http.addFilterAt(csrfFilter(), CsrfFilter.class);
        }
        //token为空过滤
        http.addFilterBefore(authorizationTokenCheckFilter(), WebAsyncManagerIntegrationFilter.class);
        //Cors过滤器
        SecurityInterceptor securityInterceptor = abSecurityInterceptor();
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
        //退出登录
        http.logout().logoutSuccessHandler(new DefualtLogoutSuccessHandler()).invalidateHttpSession(true).clearAuthentication(true);
        //无权限处理
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.headers().frameOptions().disable();
        http.authorizeRequests() //校验请求
                //忽略鉴权
                .antMatchers(ArrayUtil.removeBlank(abSecurityProperties.getAuthIngores())).permitAll()
                .and().csrf().disable();
    }

    @Override
    public void customize(WebSecurity web) {
        SecurityExpressionHandler<FilterInvocation> expressionHandler = web.getExpressionHandler();
        if(expressionHandler instanceof DefaultWebSecurityExpressionHandler){
            ((DefaultWebSecurityExpressionHandler)expressionHandler).setTrustResolver(authenticationTrustResolver());
        }
    }

    public AuthorizationTokenCheckFilter authorizationTokenCheckFilter() {
        AuthorizationTokenCheckFilter filter = new AuthorizationTokenCheckFilter();
        filter.setIgnoreUrls(ArrayUtil.removeBlank(abSecurityProperties.getAuthIngores()));
        return filter;
    }


    /**
     * 允许跨域的请求列表
     *
     * @return 实例
     */
    public RefererCsrfFilter csrfFilter() {
        RefererCsrfFilter filter = new RefererCsrfFilter();
        filter.setIngores(CollUtil.removeBlank(Arrays.asList(abSecurityProperties.getCsrfIngores())));
        return filter;
    }

    /**
     * 无权限处理器 返回resultMsg
     **/
    public DefaultAccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    /**
     * 鉴权拦截器
     *
     * @return
     */
    private SecurityInterceptor abSecurityInterceptor() {
        SecurityInterceptor intercept = new SecurityInterceptor();
        intercept.setAccessDecisionManager(abResourceRoleAuthProvider());
        intercept.setSecurityMetadataSource(securityMetadataSource());
        return intercept;
    }

    @Bean
    public AbResourceRoleAuthProvider abResourceRoleAuthProvider() {
        return new AbResourceRoleAuthProvider();
    }

    /**
     * 获取 URL 对应的角色
     *
     * @return
     */
    @Bean
    protected FilterInvocationSecurityMetadataSource securityMetadataSource() {
        AbResourceRoleRelationProvider securityMetadataSource = new AbResourceRoleRelationProvider();
        securityMetadataSource.setIgnoreUrls(ArrayUtil.removeBlank(abSecurityProperties.getAuthIngores()));
        return securityMetadataSource;
    }

    @Bean("localeResolver")
    public CookieLocaleResolver cookieLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
        return cookieLocaleResolver;
    }

    /**
     * 允许HTML 等标签的提交的请求列表
     *
     * @return 实例
     */
    public XssFilter xssFilter() {
        XssFilter xssFilter = new XssFilter();
        xssFilter.setIgnoreUrls(StrUtil.splitToArray(abSecurityProperties.getXssIngores(), StrPool.COMMA));
        return xssFilter;
    }

    /**
     * 跨域过滤器
     *
     * @return 跨域过滤器
     */
    @ConditionalOnProperty(prefix = "ab.security", name = {"enableCors", "enable-cors"}, havingValue = "true", matchIfMissing = true)
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl() {
            @Override
            public boolean isAnonymous(Authentication authentication) {
                // 兼容匿名带有Token访问
                return Objects.nonNull(authentication);
            }
        };
    }
}
