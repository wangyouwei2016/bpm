package com.dstz.cms.core.rest;


import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.cms.core.entity.CmsFastMenu;
import com.dstz.cms.core.entity.vo.CmsFastMenuVO;
import com.dstz.cms.core.manager.CmsFastMenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.base.common.constats.AbAppRestConstant.CMS_SERVICE_PREFIX;

/**
 * 快捷菜单管理 前端控制器
 *
 * @author niu
 * @since 2022-03-11
 */
@RestController
@RequestMapping(CMS_SERVICE_PREFIX + "/cmsFastMenu")
public class CmsFastMenuController extends AbCrudController<CmsFastMenu> {

    @Autowired
    private CmsFastMenuManager cmsFastMenuManager;

    @Override
    protected String getEntityDesc() {
        return "快捷菜单管理";
    }

    /**
     * 获取当前用户关联的快捷菜单
     *
     * @return List<CmsFastMenu> 快捷菜单集合
     */
    @GetMapping("fastMenuList")
    public ApiResponse<List<CmsFastMenuVO>> listByUser() {
        return success(cmsFastMenuManager.getCmsFastMenuVO(0));
    }

    /**
     * 获取当前用户关联的移动端快捷菜单
     *
     * @return List<CmsFastMenu> 快捷菜单集合
     */
    @GetMapping("fastMenuMobileList")
    public ApiResponse<List<CmsFastMenuVO>> mobileListByUser() {
        return success(cmsFastMenuManager.getCmsFastMenuVO(1));
    }

    /**
     * 新增快捷菜单
     *
     * @param id 菜单ID
     */
    @GetMapping("saveOne")
    public ApiResponse<String> saveOne(@RequestParam String id) {
        return success(cmsFastMenuManager.saveOne(id));
    }

    /**
     * 批量新增快捷菜单
     *
     * @param resourceIdList 菜单ID集合
     */
    @PostMapping("saveBatch")
    public ApiResponse<String> saveBatch(@RequestBody List<String> resourceIdList) {
        return success(() -> cmsFastMenuManager.saveBatch(resourceIdList));
    }

    /**
     * 清空当前用户的快捷菜单
     */
    @RequestMapping("removeAll")
    public ApiResponse<String> removeAll() {
        return success(() -> cmsFastMenuManager.removeAll());
    }

}
