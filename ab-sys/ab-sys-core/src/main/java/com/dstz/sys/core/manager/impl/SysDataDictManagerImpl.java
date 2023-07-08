package com.dstz.sys.core.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanConversionUtils;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.core.entity.SysDataDict;
import com.dstz.sys.core.manager.SysDataDictManager;
import com.dstz.sys.core.mapper.SysDataDictMapper;
import com.dstz.sys.rest.model.vo.SysDataDictVO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据字典 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
@Service("sysDataDictManager")
public class SysDataDictManagerImpl extends AbBaseManagerImpl<SysDataDict> implements SysDataDictManager {

    private final SysDataDictMapper sysDataDictMapper;

    public SysDataDictManagerImpl(SysDataDictMapper sysDataDictMapper) {
        this.sysDataDictMapper = sysDataDictMapper;
    }

    @Override
    public List<SysDataDict> getDictNodeList(String dictKey, Boolean hasRoot) {
        return sysDataDictMapper.getDictNodeList(dictKey, hasRoot);
    }

    @Override
    public List<SysDataDictVO> getDictTree(QueryParamDTO paramDTO) {
        AbQueryFilter filter = new DefaultAbQueryFilter(paramDTO);
        //filter.eqFilter("dictType", SysDataDict.TYPE_DICT);
        filter.noPage();
        //查询所有的字典
        List<SysDataDictVO> sysDataDictVOList = BeanCopierUtils.transformList(this.query(filter).getRows(), SysDataDictVO.class);

        if (CollUtil.isEmpty(sysDataDictVOList)) {
            return sysDataDictVOList;
        }
        //设置根
        return setRoot(BeanConversionUtils.listToTree(sysDataDictVOList));
    }

    private List<SysDataDictVO> setRoot(List<SysDataDictVO> dictTree){
        SysDataDict parent = new SysDataDict();
        parent.setName("所有数据");

        SysDataDictVO vo= BeanCopierUtils.transformBean(parent,SysDataDictVO.class);
        vo.setChildren(dictTree);
        return Arrays.asList(vo);
    }

    @Override
    public List<SysDataDictVO> getDictTypeTree() {
        //先查询数据字典的分类
        List<SysDataDict> dictTypeList = getDictNodeList("dictType", Boolean.FALSE);
        Map<String, SysDataDict> dictTypeMap = dictTypeList.stream().collect(Collectors.toMap(SysDataDict::getCode, Function.identity(), (k1, k2) -> k1));
        //查询每个分类下的字典(不包含字典项)
        List<SysDataDict> dataDictList = sysDataDictMapper.selectList(Wrappers.lambdaQuery(SysDataDict.class).eq(SysDataDict::getDictType, SysDataDict.TYPE_DICT));
        if (CollUtil.isEmpty(dataDictList)) {
            return new ArrayList<>();
        }
        Map<String, List<SysDataDict>> dataDictMap = dataDictList.stream().collect(Collectors.groupingBy(SysDataDict::getTypeCode));
        List<SysDataDictVO> result = new ArrayList<>(dictTypeList.size());
        dataDictMap.forEach((typeCode, item) -> {
            SysDataDict type = dictTypeMap.get(typeCode);
            if (type != null) {
                SysDataDictVO vo = BeanCopierUtils.transformBean(type, SysDataDictVO.class);
                vo.setChildren(BeanCopierUtils.transformList(item, SysDataDictVO.class));
                result.add(vo);
            }
        });
        return result;
    }

    private List<SysDataDictVO> getDictByTypeCode(String typeCode){
        List<SysDataDict> dictTypeList = sysDataDictMapper.selectList(Wrappers.<SysDataDict>lambdaQuery().eq(SysDataDict::getDictType,SysDataDict.TYPE_DICT)
                .eq(SysDataDict::getTypeCode,typeCode));
        return BeanCopierUtils.transformList(dictTypeList, SysDataDictVO.class);
    }


    @Override
    public int removeByIds(Collection<? extends Serializable> ids){
        for (Serializable entityId : ids){
            SysDataDict entity = sysDataDictMapper.selectById(entityId);
            if (entity == null) {
                continue;
            }
            if (Integer.valueOf(StrPool.BOOLEAN_TRUE).equals(entity.getIsSystem())){
                throw new BusinessMessage(SysApiCodes.DICT_DELETE_ERROR.formatDefaultMessage(entity.getName()));
            }
            removeByEntity(entity);
        }
        return ids.size();
    }

    /**
     * 级联删除子集
     *
     * @param entity
     */
    private void removeByEntity(SysDataDict entity) {
        List<SysDataDict> dataDictChildren = sysDataDictMapper.getByParentId(entity.getId());
        for (SysDataDict dict : dataDictChildren) {
            removeByEntity(dict);
        }
        sysDataDictMapper.deleteById(entity.getId());
    }


    @Override
    public int create(SysDataDict dataDict) {
        saveCheck(dataDict);
        return super.create(dataDict);
    }


    @Override
    public int update(SysDataDict dataDict) {
        saveCheck(dataDict);
        return super.update(dataDict);
    }

    private void saveCheck(SysDataDict dataDict) {
        String dictType = dataDict.getDictType();
        String code = dataDict.getCode();
        String id = dataDict.getId();
        String dictKey = dataDict.getDictKey();

        if (SysDataDict.TYPE_DICT.equals(dictType) && sysDataDictMapper.isExistDict(code, id) > 0) {
            throw new BusinessMessage(SysApiCodes.KEY_WORD_DUPLICATE.formatDefaultMessage("字典" + code));
        }

        if (SysDataDict.TYPE_NODE.equals(dictType) && sysDataDictMapper.isExistNode(dictKey, code, id) > 0) {
            throw new BusinessMessage(SysApiCodes.KEY_WORD_DUPLICATE.formatDefaultMessage("字典项" + code));
        }
        // 类型只能是dict node
        if (!SysDataDict.TYPE_DICT.equals(dictType) && !SysDataDict.TYPE_NODE.equals(dictType)) {
            throw new BusinessMessage(SysApiCodes.DICT_KEY_TYPE_ERROR);
        }
    }
}
