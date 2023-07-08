package com.dstz.cms.core.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.cms.core.entity.CmsComments;

/**
 * 评论管理 前端控制器
 *
 * @author niu
 * @since 2022-03-04
 */
@RestController

@RequestMapping(AbAppRestConstant.CMS_SERVICE_PREFIX + "/cmsComments")
public class CmsCommentsController extends AbCrudController<CmsComments> {

    @Override
    protected String getEntityDesc() {
        return "评论管理";
    }

}
