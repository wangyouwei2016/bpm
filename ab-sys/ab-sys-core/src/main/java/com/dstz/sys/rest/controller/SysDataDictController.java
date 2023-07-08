package com.dstz.sys.rest.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.cache.ICache;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.api.constant.SysCackeKeyConstant;
import com.dstz.sys.api.dto.DataDictDTO;
import com.dstz.sys.core.entity.SysDataDict;
import com.dstz.sys.core.manager.SysDataDictManager;
import com.dstz.sys.rest.model.dto.GetDictDTO;
import com.dstz.sys.rest.model.vo.SysDataDictVO;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/dataDict/")
public class
SysDataDictController extends AbCrudController<SysDataDict> {

    private final SysDataDictManager sysDataDictManager;

    private final ICache iCache;

    public SysDataDictController(SysDataDictManager sysDataDictManager, ICache iCache) {
        this.sysDataDictManager = sysDataDictManager;
        this.iCache = iCache;
    }

    /**
     * 通过dicKey获取字典项，数据结构是list
     */
    @RequestMapping("getDictNodeList")
    public ApiResponse<List<SysDataDict>> getByDictKey(@RequestParam("dictKey") String dictKey) {
        return ApiResponse.success(sysDataDictManager.getDictNodeList(dictKey, false));
    }


    /**
     * 通过dicKey获取字典项。若hasRoot则包含字典本身
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("getDictData")
    public ApiResponse<List<SysDataDictVO>> getByDictKey(@RequestBody @Valid GetDictDTO getDictDTO) {
        List<SysDataDictVO> nodeVoList = BeanCopierUtils.transformList(sysDataDictManager.getDictNodeList(getDictDTO.getDictKey(), getDictDTO.getHasRoot()), SysDataDictVO.class);

        //如果包含字典本身，则把字典名称做为字典项树的根节点
        if (CollUtil.isEmpty(nodeVoList) || getDictDTO.getHasRoot()) {
            return ApiResponse.success(BeanConversionUtils.listToTree(nodeVoList));
        }
        SysDataDictVO parent = null;
        //自定义根节点
        if (StrUtil.isNotEmpty(getDictDTO.getRootName())) {
            parent = new SysDataDictVO();
            parent.setParentId("0");
            parent.setName(getDictDTO.getRootName());
        } else {
            //拿到字典本身作为Root
            parent = CollUtil.findOne(nodeVoList, e -> SysDataDict.TYPE_DICT.equals(e.getDictType()));
        }

        if (parent == null) {
            return ApiResponse.success(BeanConversionUtils.listToTree(nodeVoList));
        }

        parent.setChildren(BeanConversionUtils.listToTree(nodeVoList));
        return ApiResponse.success(Arrays.asList(parent));
    }

    /**
     * 获取所有的字典（不包含字典项）
     *
     * @param queryParamDTO
     * @return
     */
    @RequestMapping("getDictList")
    public ApiResponse getDictList(@RequestBody QueryParamDTO queryParamDTO) {
        AbQueryFilter filter = new DefaultAbQueryFilter(queryParamDTO);
        filter.eqFilter("dictType", SysDataDict.TYPE_DICT);
        return ApiResponse.success(sysDataDictManager.query(filter));
    }


    @RequestMapping("getDictTypeTree")
    public ApiResponse<List<SysDataDictVO>> getDictTree() {
        return ApiResponse.success(sysDataDictManager.getDictTypeTree());
    }

    @RequestMapping("getDictTree")
    public ApiResponse<List<SysDataDictVO>> getDictTree(@RequestBody QueryParamDTO queryParamDTO) throws Exception {
        return ApiResponse.success(sysDataDictManager.getDictTree(queryParamDTO));
    }

    /**
     * 保存实体数据
     *
     * @param entity 实体
     * @return 接口响应-实体ID
     */
    @RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> save(@Valid @RequestBody SysDataDict entity) {
        String desc;
        if (StringUtils.hasLength(entity.getId())) {
            desc = "更新%s成功";
            sysDataDictManager.update(entity);
        } else {
            desc = "添加%s成功";
            sysDataDictManager.create(entity);
        }
        //把字典项放到缓存中
        putDictNodeInCache(entity.getDictKey());
        return ApiResponse.success(entity.getId()).withMessage(String.format(desc, getEntityDesc()));
    }

    private void putDictNodeInCache(String dictKey) {
        List<SysDataDict> dictNodeList = sysDataDictManager.getDictNodeList(dictKey, false);
        if (CollUtil.isNotEmpty(dictNodeList)) {
            List<DataDictDTO> data = BeanCopierUtils.transformList(dictNodeList, DataDictDTO.class);
            iCache.put(AbCacheRegionConstant.DICT_CACHE_REGION, SysCackeKeyConstant.GET_DICT_NODE_LIST + dictKey, data);
        }
    }


    /**
     * 批量保存实体数据
     *
     * @param entityList 实体集合
     * @return 接口响应-实体ID
     */
    @RequestMapping(value = "saveBatch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse saveBatch(@Valid @RequestBody List<SysDataDict> entityList) {
        entityList.forEach(entity->{
            if (StringUtils.hasLength(entity.getId())) {
                sysDataDictManager.update(entity);
            } else {
                sysDataDictManager.create(entity);
            }
        });
        //把字典项放到缓存中
        if (CollUtil.isNotEmpty(entityList)){
            Set<String> dictKeys= entityList.stream().map(SysDataDict::getDictKey).collect(Collectors.toSet());
            dictKeys.forEach(dictKey->{
                putDictNodeInCache(dictKey);
            });
        }
        return ApiResponse.success().withMessage("批量操作成功");
    }


    /**
     * 实体批量删除
     *
     * @param id 实体ID，多个,分隔
     * @return 接口响应
     */
    @RequestMapping(value = "remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> remove(@RequestParam(name = "id") String id) {
        List<SysDataDict> sysDataDicts = sysDataDictManager.selectByIds(StrUtil.split(id, CharUtil.COMMA));
        sysDataDicts.forEach(e -> {
            iCache.invalidate(AbCacheRegionConstant.DICT_CACHE_REGION, SysCackeKeyConstant.GET_DICT_NODE_LIST + e.getDictKey());
        });
        sysDataDictManager.removeByIds(StrUtil.split(id, CharUtil.COMMA));
        final String message = String.format("删除%s成功", getEntityDesc());
        return ApiResponse.<String>success().withMessage(message);
    }

    @Override
    protected String getEntityDesc() {
        return "数据字典";
    }
}
