package com.dstz.sys.core.manager;

import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.sys.rest.model.vo.SysDataDictVO;
import com.dstz.sys.core.entity.SysDataDict;
import com.dstz.base.manager.AbBaseManager;

import java.util.List;

/**
 * <p>
 * 数据字典 通用业务类
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
public interface SysDataDictManager extends AbBaseManager<SysDataDict> {
    /**
     * 通过dicKey获取字典项。若hasRoot则包含字典本身
     * @param dictKey
     * @param hasRoot
     * @return
     */
    List<SysDataDict> getDictNodeList(String dictKey, Boolean hasRoot);

    /**
     * 获取字典树
     * @param paramDTO
     * @return
     */
    List<SysDataDictVO> getDictTree(QueryParamDTO paramDTO);

    /**
     * 获取字典分类及下面的字典,构造成树桩数据结构
     * @return
     */
    List<SysDataDictVO> getDictTypeTree();

    /**
     * 新增
     * @param entry
     * @return id
     */
    @Override
    int create(SysDataDict entry);

    /**
     * 修改
     * @param entry
     * @return id
     */
    @Override
    int update(SysDataDict entry);
}
