package com.dstz.sys.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.constats.AbCacheRegionConstant;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.sys.api.constant.SysCackeKeyConstant;
import com.dstz.sys.api.dto.DataDictDTO;
import com.dstz.sys.core.entity.SysDataDict;
import com.dstz.sys.core.mapper.SysDataDictMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinxia.hou
 * @Name SysDataDictServiceImpl
 * @description:
 * @date 2022/2/2317:33
 */

@Service
public class SysDataDictApiImpl implements SysDataDictApi {

    private final SysDataDictMapper sysDataDictMapper;

    public SysDataDictApiImpl(SysDataDictMapper sysDataDictMapper) {
        this.sysDataDictMapper = sysDataDictMapper;
    }

    @Override
    @Cacheable(cacheNames = AbCacheRegionConstant.DICT_CACHE_REGION, key = SysCackeKeyConstant.GET_DICT_NODE_LIST_RECEIVE_EL)
    public List<DataDictDTO> getDictNodeList(String dictKey, Boolean hasRoot) {
        List<SysDataDict> dictNodeList = sysDataDictMapper.getDictNodeList(dictKey, hasRoot);
        if (dictNodeList == null) {
            return null;
        }
        List<DataDictDTO> dictList = new ArrayList<DataDictDTO>();
        for (SysDataDict dataDict : dictNodeList) {
            DataDictDTO dto = new DataDictDTO();
            BeanCopierUtils.copyProperties(dataDict, dto);
            dictList.add(dto);
        }
        return dictList;
    }
}
