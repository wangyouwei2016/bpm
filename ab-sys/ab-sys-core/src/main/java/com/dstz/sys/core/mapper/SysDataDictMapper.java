package com.dstz.sys.core.mapper;

import com.dstz.sys.core.entity.SysDataDict;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-11
 */
@Mapper
public interface SysDataDictMapper extends AbBaseMapper<SysDataDict> {

    /**
     * 通过dicKey获取字典项。若hasRoot则包含字典本身
     * @param dictKey
     * @param hasRoot
     * @return
     */
    List<SysDataDict> getDictNodeList(@Param("dictKey")String dictKey, @Param("hasRoot") Boolean hasRoot);

    /**
     * 判断字典是否存在，
     * @param code
     * @param id 若不为null，则排除id进行判断是否存在。用于更新时
     * @return
     */
    Integer isExistDict(@Param("code")String code,@Param("id") String id);

    /**
     * 判断字典项是否存在
     * @param dictKey
     * @param id  id 若不为null，则排除id进行判断是否存在。用于更新时
     * @return
     */
    Integer isExistNode(@Param("dictKey")String dictKey,@Param("code")String code,@Param("id") String id);

    /**
     * 通过ParentId 获取字典
     * @param id
     * @return
     */
    List<SysDataDict> getByParentId(String id);

}
