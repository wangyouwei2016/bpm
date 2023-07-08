package com.dstz.sys.api;

import com.dstz.sys.api.dto.DataDictDTO;

import java.util.List;

/**
 * @author jinxia.hou
 * @Name SysDataDictApi
 * @description: 字典
 * @date 2022/2/2317:13
 */
public interface SysDataDictApi {
    /**
     * 获取字典列表
     *
     * @param dictKey
     * @param hasRoot
     * @return
     */
    List<DataDictDTO> getDictNodeList(String dictKey, Boolean hasRoot);
}
