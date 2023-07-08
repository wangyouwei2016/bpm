package com.dstz.sys.core.manager;

import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.api.dto.QueryParamDTO;
import com.dstz.base.manager.AbBaseManager;
import com.dstz.sys.core.entity.SysDailyPhrases;

import java.util.List;

/**
 * <p>
 * 用户常用语 通用业务类
 * </p>
 *
 * @author niu
 * @since 2022-03-14
 */
public interface SysDailyPhrasesManager extends AbBaseManager<SysDailyPhrases> {

    /**
     * 分页查询
     *
     * @param paramDTO 查询参数
     */
    PageListDTO<SysDailyPhrases> listJson(QueryParamDTO paramDTO);

    /**
     * 当前用户启用的所有常用语
     *
     */
     List<SysDailyPhrases> enableList();

    /**
     * 新增或修改
     *
     * @param sysDailyPhrases 常用语对象
     */
    void saveOrUpdate(SysDailyPhrases sysDailyPhrases);
}
