package com.dstz.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dstz.base.api.dto.PageListDTO;
import com.dstz.base.entity.IPersistentEntity;
import com.dstz.base.mapper.methods.AbBulkCreate;
import com.dstz.base.query.AbQueryFilter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

/**
 * ab 通用Mapper，所有Mapper底层Mapper接口实现
 *
 * @param <T> 实体模型
 * @author wacxhs
 */
public interface AbBaseMapper<T extends IPersistentEntity> extends BaseMapper<T> {
	
	/**
	 * queryFilter 分页列表查询
	 * @param page
	 * @return
	 */
	PageListDTO<T> query(AbQueryFilter abQueryFilter);


	/**
	 * 根据实体ID完整性更新，不做字段判空
	 *
	 * @param entity 实体
	 * @return 影响行数
	 */
	int updateFullById(@Param(Constants.ENTITY) T entity);
	
	/**
	 * 批量创建
	 *
	 * @param records 记录
	 * @return 影响行数
	 */
	int bulkCreate(@Param(AbBulkCreate.METHOD_PARAM_NAME) Iterable<T> records);

	/**
	 * 根据条件查询游标
	 *
	 * @param wrapper 查询条件包裹
	 * @return 游标
	 */
	Cursor<T> queryCursorByWrapper(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
}
