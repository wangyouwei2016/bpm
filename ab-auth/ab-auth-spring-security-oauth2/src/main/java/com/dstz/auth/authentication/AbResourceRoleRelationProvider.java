package com.dstz.auth.authentication;

import cn.hutool.core.collection.CollUtil;
import com.dstz.auth.constant.PlatformConsts;
import com.dstz.auth.utils.IngoreChecker;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 根据当前的URL获取他上面分配的角色列表
 *
 * @author lightning
 */
public class AbResourceRoleRelationProvider extends IngoreChecker implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = ((FilterInvocation) object);
        HttpServletRequest request = filterInvocation.getRequest();
        // 是否匿名访问
        if (isIngores(request)) {
            return CollUtil.newHashSet(PlatformConsts.ROLE_CONFIG_ANONYMOUS);
        }
        return CollUtil.newHashSet(PlatformConsts.URL_AUTH);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
