package com.dstz.auth.core.mapper;

import com.dstz.auth.core.entity.SysApplication;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 应用 Mapper 接口
 * </p>
 *
 * @author lightning
 * @since 2022-02-07
 */
@Mapper
public interface SysApplicationMapper extends AbBaseMapper<SysApplication> {

    /**
     * 判断别名是否存在
     *
     * @param subsystem
     * @return
     */
    Integer isExist(SysApplication subsystem);

    /**
     * 更新为默认。
     */
    void updNoDefault();

    /**
     * 根据code获取系统
     * @return
     */
    SysApplication getByAlias(String systemAlias);
}
