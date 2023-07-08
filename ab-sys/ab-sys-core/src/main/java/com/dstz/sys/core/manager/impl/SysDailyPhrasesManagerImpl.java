package com.dstz.sys.core.manager.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.base.query.AbQueryFilter;
import com.dstz.base.query.ConditionType;
import com.dstz.base.query.impl.DefaultAbQueryFilter;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.core.entity.SysDailyPhrases;
import com.dstz.sys.core.manager.SysDailyPhrasesManager;
import com.dstz.sys.core.mapper.SysDailyPhrasesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.dstz.base.api.vo.ApiResponse.success;
import static com.dstz.sys.api.constant.SysApiCodes.INIT_DATA_CANT_DELETE;

/**
 * 用户常用语 通用服务实现类
 *
 * @author niu
 * @since 2022-03-14
 */
@Service("sysDailyPhrasesManager")
public class SysDailyPhrasesManagerImpl extends AbBaseManagerImpl<SysDailyPhrases> implements SysDailyPhrasesManager {

    @Autowired
    private SysDailyPhrasesMapper sysDailyPhrasesMapper;

    /**
     * 分页查询
     *
     * @param paramDTO 查询条件
     */
    @Override
    public PageListDTO<SysDailyPhrases> listJson(QueryParamDTO paramDTO) {
        DefaultAbQueryFilter filter = new DefaultAbQueryFilter(paramDTO);
        filter.addParam("userId", UserContextUtils.getUserId());
        return sysDailyPhrasesMapper.listJson(filter);
    }


    /**
     * 查询当前用户的所有常用语
     */
    @Override
    public List<SysDailyPhrases> enableList() {
        AbQueryFilter filter = new DefaultAbQueryFilter(new QueryParamDTO());
        filter.addParam("userId", UserContextUtils.getUserId());
        filter.addFilter("enable", 1, ConditionType.EQUAL);
        filter.noPage();
        return sysDailyPhrasesMapper.listJson(filter);
    }


    /**
     * 新增或修改
     *
     * @param sysDailyPhrases 常用语对象
     */
    @Override
    public void saveOrUpdate(SysDailyPhrases sysDailyPhrases) {
        validate(sysDailyPhrases);
        if (StrUtil.isEmpty(sysDailyPhrases.getId())) {
            sysDailyPhrases.setUpdateTime(new Date());
        }
        super.createOrUpdate(sysDailyPhrases);
    }

    /**
     * 查重操作
     *
     * @param sysDailyPhrases: 修改后的对象
     **/
    private void validate(SysDailyPhrases sysDailyPhrases) {
        //别名查重
        if (StrUtil.isEmpty(sysDailyPhrases.getId()) || !StrUtil.equals(getById(sysDailyPhrases.getId()).getLocution(), sysDailyPhrases.getLocution())) {
            if (selectCount(Wrappers.lambdaQuery(SysDailyPhrases.class)
                    .eq(SysDailyPhrases::getLocution, sysDailyPhrases.getLocution())
                    .eq(SysDailyPhrases::getCreateBy, UserContextUtils.getUserId())) > 0) {
                throw new BusinessMessage(SysApiCodes.CODE_DUPLICATE);
            }
        }
    }

    /**
     * 删除批量删除常用语(内置数据禁止删除)
     **/
    @Override
    public int removeByIds(Collection<? extends Serializable> list) {
        List<SysDailyPhrases> sysDailyPhrases = selectByWrapper(Wrappers.<SysDailyPhrases>lambdaQuery().in(SysDailyPhrases::getId, list));
        for (SysDailyPhrases sysDailyPhrase : sysDailyPhrases) {
            if (sysDailyPhrase.getIsDefault() == 1) {
                throw new BusinessMessage(INIT_DATA_CANT_DELETE.formatDefaultMessage(sysDailyPhrase.getLocution()));
            }
        }
        return super.removeByIds(list);
    }
}
