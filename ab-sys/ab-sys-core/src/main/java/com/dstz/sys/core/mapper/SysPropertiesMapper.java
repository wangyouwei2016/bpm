package com.dstz.sys.core.mapper;

import com.dstz.sys.core.entity.SysProperties;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统属性 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2022-02-11
 */
@Mapper
public interface SysPropertiesMapper extends AbBaseMapper<SysProperties> {

    /**
     * 判断属性是否存在。
     *
     * @param sysProperties
     * @return
     */
    Integer isExist(SysProperties sysProperties);

    /**
     * 分组列表。
     *
     * @return
     */
    @Select("SELECT distinct group_ FROM SYS_PROPERTIES")
    List<String> getGroups();

}
