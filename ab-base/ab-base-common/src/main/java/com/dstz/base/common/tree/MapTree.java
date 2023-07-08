package com.dstz.base.common.tree;

import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.map.MapUtil;
import com.dstz.base.api.model.Tree;
import com.dstz.base.common.utils.CastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 以Map作为树实现
 *
 * @author wacxhs
 */
public class MapTree extends HashMap<String, Object> implements Tree<MapTree> {

	private static final long serialVersionUID = -8739914867159343333L;

	private final TreeNodeConfig treeNodeConfig;

	public MapTree(TreeNodeConfig treeNodeConfig) {
		this.treeNodeConfig = treeNodeConfig;
	}

	public MapTree(int initialCapacity, TreeNodeConfig treeNodeConfig) {
		super(initialCapacity);
		this.treeNodeConfig = treeNodeConfig;
	}

	public static MapTree buildDefaultConfigMapTree(String id, String parentId, String name, Map<String, Object> extra) {
		return buildMapTree(TreeNodeConfig.DEFAULT_CONFIG, id, parentId, name, extra);
	}

	public static MapTree buildMapTree(TreeNodeConfig treeNodeConfig, String id, String parentId, String name, Map<String, Object> extra) {
		MapTree mapTree = new MapTree(treeNodeConfig);
		mapTree.setId(id);
		mapTree.setParentId(parentId);
		mapTree.put(treeNodeConfig.getNameKey(), name);
		if (MapUtil.isNotEmpty(extra)) {
			mapTree.putAll(extra);
		}
		return mapTree;
	}

	/**
	 * 设置ID
	 *
	 * @param id id
	 */
	public void setId(String id) {
		put(treeNodeConfig.getIdKey(), id);
	}

	@Override
	public String getId() {
		return MapUtil.getStr(this, treeNodeConfig.getIdKey());
	}

	/**
	 * 设置上级ID
	 *
	 * @param parentId 上级ID
	 */
	public void setParentId(String parentId) {
		put(treeNodeConfig.getParentIdKey(), parentId);
	}

	@Override
	public String getParentId() {
		return MapUtil.getStr(this, treeNodeConfig.getParentIdKey());
	}

	@Override
	public List<MapTree> getChildren() {
		return CastUtils.cast(get(treeNodeConfig.getChildrenKey()));
	}

	@Override
	public void setChildren(List<MapTree> list) {
		put(treeNodeConfig.getChildrenKey(), list);
	}
}
