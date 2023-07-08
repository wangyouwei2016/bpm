package com.dstz.cms.core.rest;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.api.vo.PageListVO;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.cms.core.entity.CmsNews;
import com.dstz.cms.core.entity.dto.CmsNewsDTO;
import com.dstz.cms.core.manager.CmsNewsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.base.common.constats.AbAppRestConstant.CMS_SERVICE_PREFIX;

/**
 * 新闻管理 前端控制层
 *
 * @author niu
 * @since 2022-03-04
 */
@RestController
@RequestMapping(CMS_SERVICE_PREFIX + "/cmsNews")
public class CmsNewsController extends AbCrudController<CmsNews> {

    @Autowired
    private CmsNewsManager cmsNewsManager;

    @Override
    protected String getEntityDesc() {
        return "新闻管理";
    }

    /**
     * 分页列表
     */
    @Override
    @PostMapping("listJson")
    public ApiResponse<PageListVO<CmsNews>> listJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        PageListDTO<CmsNews> pageList = cmsNewsManager.listJson(queryParamDto);
        return ApiResponse.success(pageList);
    }

    /**
     * 首页获取固定两条,精简后的新闻(剔除附件,和其他无用字段)
     */
    @PostMapping("getNewsPage")
    public ApiResponse<PageListVO<CmsNews>> getNewsPage(@RequestBody QueryParamDTO queryParamDto) {
        return success(cmsNewsManager.getNewsPage(queryParamDto));
    }

    /**
     * 阅读了指定的新闻
     *
     * @param id 新闻ID

     */
    @GetMapping("read")
    public ApiResponse<String> read(@RequestParam String id) {
        return success(() -> cmsNewsManager.update(null, Wrappers.lambdaUpdate(CmsNews.class)
                .eq(CmsNews::getId, id)
                .setSql("visit_num_ = visit_num_ + 1")));
    }

    /**
     * 查询指定ID的新闻内容
     *
     * @param id 新闻ID
     * @return ApiResponse 通用返回对象, 包含展示的新闻详情
     */
    @GetMapping("getOne")
    public ApiResponse<CmsNewsDTO> getById(@RequestParam String id) {
        return success(cmsNewsManager.details(id));
    }

    /**
     * 新增或修改新闻
     *
     * @param cmsNews 新闻对象
     * @return 接口响应对象 ApiResponse
     */
    @RequestMapping("saveOrUpdate")
    public ApiResponse<String> saveOrUpdate(@RequestBody CmsNews cmsNews) {
        return success(() -> cmsNewsManager.saveOrUpdate(cmsNews));
    }

    /**
     * 发布新闻
     *
     * @param id 新闻id
     */
    @RequestMapping("releaseNews/{id}")
    public ApiResponse<String> releaseNews(@PathVariable String id) {
        return success(() -> cmsNewsManager.releaseNews(id));
    }

    /**
     * 下架新闻
     *
     * @param id 新闻id
     */
    @RequestMapping("withdrawNews/{id}")
    public ApiResponse<String> withdrawNews(@PathVariable String id) {
        return success(() -> cmsNewsManager.withdrawNews(id));
    }

}
