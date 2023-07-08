package com.dstz.cms.core.rest;


import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.api.vo.PageListVO;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.cms.core.entity.CmsNotify;
import com.dstz.cms.core.entity.dto.CmsNotifyDTO;
import com.dstz.cms.core.entity.vo.CmsNotifyListVO;
import com.dstz.cms.core.entity.vo.CmsNotifyVO;
import com.dstz.cms.core.manager.CmsNotifyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.base.common.constats.AbAppRestConstant.CMS_SERVICE_PREFIX;

/**
 * 系统公告表 前端控制器
 *
 * @author niu
 * @since 2022-02-28
 */
@RestController
@RequestMapping(CMS_SERVICE_PREFIX + "/cmsNotify")
public class CmsNotifyController extends AbCrudController<CmsNotify> {

    @Autowired
    private CmsNotifyManager cmsNotifyManager;

    @Override
    protected String getEntityDesc() {
        return "系统公告模块";
    }

    /**
     * 分页查询
     *
     * @param queryParamDto 查询对象
     * @return PageListDTO 展示的公告列表
     */
    @RequestMapping("listJson")
    @Override
    public ApiResponse<PageListVO<CmsNotifyVO>> listJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        return success(cmsNotifyManager.page(queryParamDto));
    }

    /**
     * 查询指定ID的公告内容
     *
     * @param id 公告ID
     * @return ApiResponse 通用返回对象, 包含展示的公告详情
     */
    @GetMapping("getOne")
    public ApiResponse<CmsNotifyVO> getById(@RequestParam("id") String id) {
        return success(cmsNotifyManager.details(id));
    }

    /**
     * 查看所属公告列表 (筛选公告关联的组织)
     */
    @RequestMapping("getNotifyPage")
    public ApiResponse<PageListVO<CmsNotifyListVO>> getNotifyPage(@RequestBody QueryParamDTO queryParamDto) {
        return success(cmsNotifyManager.getNotifyPage(queryParamDto));
    }

    /**
     * 创建公告
     *
     * @param cmsNotifyDTO 新增的公告DTO对象
     */
    @RequestMapping("saveDto")
    public ApiResponse<String> saveDto(@RequestBody CmsNotifyDTO cmsNotifyDTO) {
        return success(() -> cmsNotifyManager.save(cmsNotifyDTO));
    }

    /**
     * 修改公告
     *
     * @param cmsNotifyDTO 修改的的公告DTO对象
     */
    @RequestMapping("updateDto")
    public ApiResponse<String> updateDto(@RequestBody CmsNotifyDTO cmsNotifyDTO) {
        return success(() -> cmsNotifyManager.update(cmsNotifyDTO));
    }


    /**
     * 查询用户未读公告数量
     */
    @RequestMapping("queryUnReadCount")
    public ApiResponse<Integer> queryUnReadCount() {
        return success(cmsNotifyManager.queryUnReadCount());
    }

    /**
     * 发布公告
     *
     * @param id 公告id
     */
    @RequestMapping("releaseNotify/{id}")
    public ApiResponse<String> releaseNotify(@PathVariable String id) {
        return success(() -> cmsNotifyManager.releaseNotify(id));
    }

    /**
     * 下架公告
     *
     * @param id 公告id
     */
    @RequestMapping("withdrawNotify/{id}")
    public ApiResponse<String> withdrawNotify(@PathVariable String id) {
        return success(() -> cmsNotifyManager.withdrawNotify(id));
    }

}
