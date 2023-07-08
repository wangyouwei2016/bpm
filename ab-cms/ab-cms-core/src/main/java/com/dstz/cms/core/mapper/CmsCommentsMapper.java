package com.dstz.cms.core.mapper;

import com.dstz.cms.core.entity.CmsComments;
import com.dstz.base.mapper.AbBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author niu
 * @since 2022-03-04
 */
@Mapper
public interface CmsCommentsMapper extends AbBaseMapper<CmsComments> {

}
