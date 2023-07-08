package com.dstz.cms.core.rest;


import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.cms.core.entity.CmsFrequentUsed;
import com.dstz.cms.core.manager.CmsFrequentUsedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.base.common.constats.AbAppRestConstant.CMS_SERVICE_PREFIX;

/**
 * <p>
 * 常用流程管理 前端控制器
 * </p>
 *
 * @author niu
 * @since 2022-03-11
 */
@RestController
@RequestMapping(CMS_SERVICE_PREFIX + "/cmsFrequentUsed")
public class CmsFrequentUsedController extends AbCrudController<CmsFrequentUsed> {

    @Autowired
    private CmsFrequentUsedManager cmsFrequentUsedManager;

    @Override
    protected String getEntityDesc() {
        return "常用流程管理";
    }

    /**
     * 获取当前用户的所有常用流程
     *
     * @return List<CmsFrequentUsed> 常用流程集合
     */
    @RequestMapping("list")
    public ApiResponse<List<CmsFrequentUsed>> list() {
        return success(cmsFrequentUsedManager.list());
    }

    /**
     * 批量新增常用流程
     *
     * @param defIdList 常用流程ID集合
     */
    @RequestMapping("saveBatch")
    public ApiResponse<String> saveBatch(@RequestBody List<String> defIdList) {
        return success(() -> cmsFrequentUsedManager.saveBatch(defIdList));
    }

}
