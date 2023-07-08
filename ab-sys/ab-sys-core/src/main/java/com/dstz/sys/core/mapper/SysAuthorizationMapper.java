package com.dstz.sys.core.mapper;

import com.dstz.base.mapper.AbBaseMapper;
import com.dstz.sys.core.entity.SysAuthorization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通用资源授权配置 Mapper 接口
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Mapper
public interface SysAuthorizationMapper extends AbBaseMapper<SysAuthorization> {
    List<SysAuthorization> getByTarget(@Param("rightsObject") String rightsObject, @Param("rightsTarget") String rightsTarget);

    void deleteByTarget(@Param("rightsObject") String rightsObject, @Param("rightsTarget") String rightsTarget);
}
