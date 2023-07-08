package com.dstz.auth.rest.model.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 角色资源分配
 * 
 * @author wacxhs
 */
public class GrantRoleResourceDTO {

	/**
	 * 角色ID
	 */
	@NotEmpty(message = "角色ID不能为空")
	private String roleId;
	
	/**
	 * 应用ID
	 */
	@NotEmpty(message = "应用ID不能为空")
	private String appId;
	
	/**
	 * 资源ID
	 */
	private Set<String> resIds;

	/**
	 * 半选中资源
	 */
	private Set<String> halfResIds;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Set<String> getResIds() {
		return resIds;
	}

	public void setResIds(Set<String> resIds) {
		this.resIds = resIds;
	}

	public Set<String> getHalfResIds() {
		return halfResIds;
	}

	public void setHalfResIds(Set<String> halfResIds) {
		this.halfResIds = halfResIds;
	}

	@Override
	public String toString() {
		return "GrantRoleResourceDTO{" +
				"roleId='" + roleId + '\'' +
				", appId='" + appId + '\'' +
				", resIds=" + resIds +
				", halfResIds=" + halfResIds +
				'}';
	}
}
