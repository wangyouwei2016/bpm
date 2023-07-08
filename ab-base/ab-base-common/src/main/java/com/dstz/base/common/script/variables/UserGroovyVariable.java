package com.dstz.base.common.script.variables;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.utils.CastUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.org.api.GroupApi;
import com.dstz.org.api.UserApi;
import com.dstz.org.api.enums.GroupType;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;

import java.util.List;

/**
 * groovy 中 通过对象 懒加载 用户信息
 * 比如  脚本：return ( startUser.orgType.equals( "1" ) && startUser.username.equals( "" ) )
 * @author jeff
 *
 */
public class UserGroovyVariable {
	private IUser user;
	private IGroup org;
	private RoleListVariable role;
	
	// 没有设置时通过id加载
	private String orgId;
	private String userId;
	
	
	/**
	 * 有用户，组织信息需要懒加载
	 * @param user
	 * @param orgId
	 */
	public UserGroovyVariable(IUser user, String orgId) {
		this.user = user;
		this.orgId = orgId;
	}
	/**
	 * 只有用户，组织取当前组织
	 * @param user2
	 */
	public UserGroovyVariable(IUser user2) {
		this.user = user2;
 	}
	
	/**
	 *  用户，组织都只有id时
	 * @param userId
	 * @param orgId
	 */
	public UserGroovyVariable(String userId, String orgId) {
		this.userId = userId;
		this.orgId = orgId;
	}

	public String getId() {
		return getUser().getUserId();
	}
	

	private IUser getUser() {
		if(user != null) return user;
		
		if(StrUtil.isNotBlank(userId)) {
			UserApi userApi = SpringUtil.getBean(UserApi.class);
			Assert.notNull(userApi, "请检查组织服务注入情况！");
			user = userApi.getByUserId(userId);
		}
		
		return user;
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public IGroup getOrg() {
		if(org != null)return org;
		
		// 指定ORG
		if( StrUtil.isNotBlank(orgId)) {
			GroupApi groupApi = SpringUtil.getBean(GroupApi.class);
			Assert.notNull(groupApi, "请检查组织服务注入情况！");
			
			org = groupApi.getByGroupId(GroupType.ORG.getType(), orgId);
			return org;
		} 
		
		// 获取当前组织
		org = UserContextUtils.getGroup().orElse(null);
		if(org == null) {
			org =  new EmptyOrg();
		}
		return org;
	}

	public RoleListVariable getRole() {
		if(role == null) {
			GroupApi groupApi = SpringUtil.getBean(GroupApi.class);
			Assert.notNull(groupApi, "请检查组织服务注入情况！");
			List<? extends IGroup> roleList = groupApi.getByGroupTypeAndUserId(GroupType.ROLE.getType(), getUser().getUserId());
			this.role = new RoleListVariable(CastUtils.cast(roleList));
		}
		
		return role;
	}
	
	
	
	 /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getUserId() {
		return getUser().getUserId();
	}
    /**
     * 获取属性值
     *
     * @param attrName 属性名称
     * @param tClass   属性类型
     * @param <T>      T
     * @return 属性值
     */
    <T> T getAttrValue(String attrName, Class<T> tClass) {
		return getUser().getAttrValue(attrName, tClass);
	}
    
    
	public String getOrgId() {
		return getOrg().getGroupId();
	}
	
 
	public String getOrgName() {
		return getOrg().getGroupName();
	}

	public Integer getOrgType() {
		return getOrg().getAttrValue("type", Integer.class);
	}

	public String getOrgCode() {
		return getOrg().getGroupCode();
	}
}
