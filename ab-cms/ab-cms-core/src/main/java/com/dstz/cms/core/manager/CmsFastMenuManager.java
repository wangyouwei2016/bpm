package com.dstz.cms.core.manager;

import com.dstz.base.manager.AbBaseManager;
import com.dstz.cms.core.entity.CmsFastMenu;
import com.dstz.cms.core.entity.vo.CmsFastMenuVO;

import java.util.List;

/**
 * <p>
 * 快捷菜单管理 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-03-11
 */
public interface CmsFastMenuManager extends AbBaseManager<CmsFastMenu> {

    /**
     * 获取当前用户的快捷菜单（常用应用）
     * @param type 是否为移动端  移动端：1    PC端：0
     */
    List<CmsFastMenuVO> getCmsFastMenuVO(int type);

    /**
     * 清空当前用户的快捷菜单
     */
    void removeAll();

    /**
     * 批量新增快捷菜单
     *
     * @param resourceIdList 菜单ID集合
     */
    void saveBatch(List<String> resourceIdList);

    /**
     * 新增快捷菜单
     *
     * @param id 菜单ID集合
     */
    String saveOne(String id);
}
