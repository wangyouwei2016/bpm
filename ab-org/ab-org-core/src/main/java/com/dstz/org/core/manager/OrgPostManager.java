package com.dstz.org.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.org.core.entity.Role;

import java.util.Collection;
import java.util.List;

/**
 * 组织-岗位通用业务处理
 *
 * @author wacxhs
 */
public interface OrgPostManager {

	/**
	 * 根据主键ID获取岗位列表
	 *
	 * @param ids 组织ID集
	 * @return 岗位列表
	 */
	List<Role> getByIds(Collection<String> ids);

	/**
	 * 查询岗位集
	 *
	 * @param queryFilter 查询过滤器
	 * @return 岗位列表
	 */
	PageListDTO<Role> queryPosts(AbQueryFilter queryFilter);
}
