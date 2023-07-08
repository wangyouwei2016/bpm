package com.dstz.sys.rest.controller;


import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.base.common.constats.AbAppRestConstant.SYS_SERVICE_PREFIX;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.api.vo.PageListVO;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.query.ConditionType;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.core.entity.SysDailyPhrases;
import com.dstz.sys.core.manager.SysDailyPhrasesManager;

/**
 * <p>
 * 用户常用语 前端控制器
 * </p>
 *
 * @author niu
 * @since 2022-03-14
 */
@RestController
@RequestMapping(SYS_SERVICE_PREFIX + "/sysDailyPhrases")
public class SysDailyPhrasesController extends AbCrudController<SysDailyPhrases> {

    @Autowired
    private SysDailyPhrasesManager sysDailyPhrasesManager;

    @Override
    protected String getEntityDesc() {
        return "用户常用语";
    }

    /**
     * 分页查询
     *
     * @param queryParamDto 查询对象
     * @return PageListDTO 展示的公告列表
     */
    @RequestMapping("listJson")
    @Override
    public ApiResponse<PageListVO<SysDailyPhrases>> listJson(@Valid @RequestBody QueryParamDTO queryParamDto) {
        return success(sysDailyPhrasesManager.listJson(queryParamDto));
    }

    /**
     * 保存或修改实体数据
     *
     * @param sysDailyPhrases 常用语对象
     * @return 接口响应对象
     */
    @PostMapping("saveOrUpdate")
    public ApiResponse<String> saveOrUpdate(@RequestBody SysDailyPhrases sysDailyPhrases) {
        return success(() -> sysDailyPhrasesManager.saveOrUpdate(sysDailyPhrases));
    }



    /**
     * 启动或禁用
     *
     * @param sysDailyPhrases 常用语对象
     */
    @PostMapping("updateEnable")
    public ApiResponse<String> updateEnable(@RequestBody SysDailyPhrases sysDailyPhrases) {
        return success(() -> sysDailyPhrasesManager.update(null,Wrappers.lambdaUpdate(SysDailyPhrases.class)
                .eq(SysDailyPhrases::getId,sysDailyPhrases.getId())
                .set(SysDailyPhrases::getEnable,sysDailyPhrases.getEnable())));
    }

    /**
     * 获取当前用户的所有常用语 (筛选已启用)
     *
     * @return List<SysDailyPhrases> 常用语集合
     */
    @GetMapping("list")
    public ApiResponse<List<SysDailyPhrases>> list() {
        return success(sysDailyPhrasesManager.enableList());
    }

}
