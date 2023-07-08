package com.dstz.base.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.context.UserContext;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;

import java.util.Optional;
import java.util.Set;

/**
 * 用户上下文工具类
 *
 * @author wacxhs
 */
public class UserContextUtils {

    private static volatile UserContext userContext;

    private UserContextUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * 获取用户上下文
     *
     * @return 用户上下文
     */
    public static UserContext getUserContext() {
        if (userContext == null) {
            synchronized (UserContextUtils.class) {
                userContext = SpringUtil.getBean(UserContext.class);
            }
        }
        return userContext;
    }

    /**
     * 获取用户,请判空
     *
     * @return 用户
     */
    public static Optional<IUser> getUser() {
        return ObjectUtil.defaultIfNull(getUserContext().getUser(), Optional.empty());
    }
    
    /**
     * 强依赖当前用户的接口场景<br/>
     * 如果你的方法有可能出现在没登录用户场景的，勿用
     * @return
     */
    public static IUser getValidUser() {
        return getUserContext().getUser().orElseThrow(() -> new BusinessException(GlobalApiCodes.NO_LOGIN_USER));
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static String getUserId() {
        return getUser().map(IUser::getUserId).orElse(null);
    }

    /**
     * 获取用户姓名
     *
     * @return 获取用户姓名
     */
    public static String getUserName() {
        return getUser().map(IUser::getFullName).orElse(null);
    }

    /**
     * 获取用户账户
     *
     * @return 用户账户
     */
    public static String getAccount(){
        return getUser().map(IUser::getUsername).orElse(null);
    }

    /**
     * 获取组织
     *
     * @return 组织
     */
    public static Optional<IGroup> getGroup() {
        return ObjectUtil.defaultIfNull(getUserContext().getOrg(), Optional.empty());
    }

    /**
     * 获取当前组织ID
     *
     * @return 组织ID
     */
    public static String getGroupId() {
        return getUserContext().getOrg().map(IGroup::getGroupId).orElse(null);
    }

    /**
     * 是否超级管理员
     *
     * @return 是否超级管理员
     */
    public static boolean isSuperAdmin() {
        return getUserContext().isSuperAdmin();
    }

    /**
     * 清除当前执行人。
     * void
     */
    public static void clear() {
         getUserContext().clear();
    }
    
    /**
     * 
     * @param userId
     * @return
     */
	public static Set<String> getFilterUserIds(String userId) {
		// TODO 返回 工作交接
		return CollectionUtil.newHashSet(UserContextUtils.getUserId());
	}
}
