package com.dstz.cms.core.manager.impl;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.auth.authentication.api.SysResourceApi;
import com.dstz.auth.authentication.api.model.ISysResource;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.cms.core.entity.CmsFastMenu;
import com.dstz.cms.core.entity.vo.CmsFastMenuVO;
import com.dstz.cms.core.manager.CmsFastMenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 快捷菜单管理 通用服务实现类
 *
 * @author niu
 * @since 2022-03-11
 */
@Service("cmsFastMenuManager")
public class CmsFastMenuManagerImpl extends AbBaseManagerImpl<CmsFastMenu> implements CmsFastMenuManager {

    @Autowired
    private SysResourceApi sysResourceApi;

    @Override
    public List<CmsFastMenuVO> getCmsFastMenuVO(int type) {
        List<CmsFastMenu> cmsFastMenus = selectByWrapper(Wrappers.<CmsFastMenu>lambdaQuery()
                .eq(CmsFastMenu::getUserId, UserContextUtils.getUserId())
                .eq(CmsFastMenu::getMobile, type));
        List<CmsFastMenuVO> result = new ArrayList<>();
        for (CmsFastMenu fastMenu : cmsFastMenus) {
            ISysResource resource = sysResourceApi.getResourceById(fastMenu.getResourceId());
            if (resource != null) {
                CmsFastMenuVO vo = new CmsFastMenuVO(fastMenu.getId(), fastMenu.getUserId(), fastMenu.getResourceId(), resource.getName(), resource.getUrl(), resource.getIcon());
                result.add(vo);
            }
        }
        //如果为移动端，则需判断常用应用里是否包含快捷申请， 不包含就添加上去。
        if (type == 1) {
            ISysResource todo = sysResourceApi.getTodoResource();
            if (result.stream().noneMatch(s -> StrUtil.equals(s.getResourceId(), todo.getId()))) {
                result.add(new CmsFastMenuVO(todo.getId(), todo.getName(), todo.getUrl(), todo.getIcon()));
            }
        }
        return result;
    }

    @Override
    public String saveOne(String id) {
        CmsFastMenu cmsFastMenu = new CmsFastMenu(id);
        create(cmsFastMenu);
        return cmsFastMenu.getId();
    }

    /**
     * 批量新增快捷菜单
     *
     * @param resourceIdList 菜单ID集合
     */
    @Override
    public void saveBatch(List<String> resourceIdList) {
        removeAll();
        Iterable<CmsFastMenu> cmsFastMenuIterable = IterUtil.asIterable(resourceIdList.stream().map(s ->
                new CmsFastMenu(UserContextUtils.getUserId(), s)).iterator());
        bulkCreate(cmsFastMenuIterable);
    }

    /**
     * 清空当前用户的快捷菜单
     */
    @Override
    public void removeAll() {
        remove(Wrappers.<CmsFastMenu>lambdaQuery().eq(CmsFastMenu::getUserId, UserContextUtils.getUserId()));
    }

}
