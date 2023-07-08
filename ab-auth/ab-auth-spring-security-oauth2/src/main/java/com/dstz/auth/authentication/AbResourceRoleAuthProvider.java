package com.dstz.auth.authentication;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dstz.auth.authentication.api.SysResourceApi;
import com.dstz.auth.constant.PlatformConsts;
import com.dstz.base.common.utils.UserContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 访问决策管理器。 通过当前资源需要角色，判断用户是否有这些角色，若有则通过
 *
 * @author lightning
 */
public class AbResourceRoleAuthProvider implements AccessDecisionManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysResourceApi sysResourceApi;

    /**
     * 判断 当前用户所拥有的角色 是否满足 当前资源所需的角色
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_ANONYMOUS)) {
            return;
        }
        if (authentication.isAuthenticated()) {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            final String servletPath = filterInvocation.getRequest().getServletPath();

            if (UserContextUtils.isSuperAdmin()) {
                logger.trace("路径：{}, 当前访问用户为超级管理员, 跳过资源管控", servletPath);
                return;
            }

            Set<String> accessRoleByUrl = sysResourceApi.getAccessRoleByUrl(servletPath);
            if (CollUtil.isEmpty(accessRoleByUrl)) {
                logger.trace("路径：{}, 未查询到授权角色，当前访问将不做权限校验", servletPath);
                return;
            }

            // 遍历当前用户的角色，如果当前页面对应的角色包含当前用户的某个角色则有权限访问。
            for (GrantedAuthority grantedAuthority : ObjectUtil.<Collection<? extends GrantedAuthority>>defaultIfNull(authentication.getAuthorities(), Collections.emptyList())) {
                if (accessRoleByUrl.contains(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("对不起,您没有该资源访问权限");
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
