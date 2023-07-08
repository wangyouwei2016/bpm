package com.dstz.auth.authentication.api;

import com.dstz.auth.authentication.api.model.ISysApplication;
import com.dstz.auth.authentication.api.model.ISysResource;

import java.util.List;
import java.util.Set;
/**
 * 系统资源接口
 *
 * @author lightning
 */
public interface SysResourceApi {

	/**
	 * 通过资源id获取资源对象
	 * @param  id  资源ID
	 * @return ISysResource 资源对象
	 */
	ISysResource getResourceById(String id);

	/**
	 * 通过资源code集合删除资源对象
	 * @param  code  资源code集合
	 */
	 void deleteResourceByCode(List<String> code);

	/**
	 * 获取当前用户拥有的系统
	 * @return
	 */
	List<ISysApplication> getCurrentUserSystem();
	/**
	 * 获取默认系统
	 * @return
	 */
	ISysApplication getDefaultSystem(String currentUserId);
	/**
	 * 根据id获取系统资源
	 * @return
	 */
	List<ISysResource> getBySystemId(String systemId);
	/**
	 * 根据systemId和userId获取系统资源
	 * @return
	 */
	List<ISysResource> getByAppIdAndUser(String appId, String userId);
	/**
	 * 根据url获取拥有资源
	 * @return
	 */
	Set<String> getAccessRoleByUrl(String url);

	/**
	 * 根据url查询是url还是方法接口
	 */
	boolean isRoleByUrl(String url);

	/**
	 * 获取移动端办理事项的资源
	 */
	ISysResource getTodoResource();
}
