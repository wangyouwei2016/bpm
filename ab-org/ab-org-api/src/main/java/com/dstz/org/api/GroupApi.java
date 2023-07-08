package com.dstz.org.api;

import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.org.api.enums.GroupType;
import com.dstz.org.api.model.IGroup;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组接口
 *
 * @author wacxhs
 */
public interface GroupApi {

	/**
	 * 根据组类型和组ID获取组记录
	 *
	 * @param groupType 组类型
	 * @param groupId   组ID集
	 * @return 组记录
	 */
	default IGroup getByGroupId(String groupType, String groupId) {
		Iterator<? extends IGroup> iterator = getByGroupIds(groupType, Collections.singleton(groupId));
		return Objects.nonNull(iterator) && iterator.hasNext() ? iterator.next() : null;
	}

	/**
	 * 根据组类型和组ID获取组记录
	 *
	 * @param groupType 组类型
	 * @param groupIds  组ID集
	 * @return 组记录
	 */
	Iterator<? extends IGroup> getByGroupIds(String groupType, Collection<String> groupIds);

	/**
	 * 根据组类型和组编码获取组
	 *
	 * @param groupType 组类型
	 * @param groupCode 组编码
	 * @return 组记录
	 */
	default IGroup getByGroupCode(String groupType, String groupCode) {
		Iterator<? extends IGroup> iterator = getByGroupCodes(groupType, Collections.singleton(groupCode));
		return Objects.nonNull(iterator) && iterator.hasNext() ? iterator.next() : null;
	}

	/**
	 * 通过组类型和组编码获取组记录
	 *
	 * @param groupType  组类型
	 * @param groupCodes 组编码
	 * @return 组记录
	 */
	Iterator<? extends IGroup> getByGroupCodes(String groupType, Collection<String> groupCodes);

	/**
	 * 根据用户ID获取所关联组
	 *
	 * @param userId 用户ID
	 * @return 关联组
	 */
	default List<? extends IGroup> getByUserId(String userId) {
		return Arrays.stream(GroupType.values()).map(o -> getByGroupTypeAndUserId(o.getType(), userId)).flatMap(Collection::stream).collect(Collectors.toList());
	}

	/**
	 * 根据组类型和用户ID获取组记录列表
	 *
	 * @param groupType 组类型
	 * @param userId    用户编号
	 * @return 组记录
	 */
	List<? extends IGroup> getByGroupTypeAndUserId(String groupType, String userId);

	/**
	 * 组记录查询过滤
	 *
	 * @param groupType     组类型
	 * @param queryParamDTO 过滤参数
	 * @return 组记录
	 */
	default List<? extends IGroup> queryFilter(String groupType, QueryParamDTO queryParamDTO) {
		return null;
	}
}
