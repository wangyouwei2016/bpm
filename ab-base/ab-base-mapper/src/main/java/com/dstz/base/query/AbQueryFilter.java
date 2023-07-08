package com.dstz.base.query;

import com.dstz.base.query.impl.DefaultQueryFieldGroup;

import java.util.List;
import java.util.Map;

/**
 * 查询构造器API
 * @author Jeff
 *
 */
public interface AbQueryFilter {
	
	// where sql 预定名字
	static final String WHERE_SQL = "whereSql";
	// orderBySql sql 预定名字
	static final String ORDERBY_SQL = "orderBySql";
	
	/**
	 * 分页
	 * @return
	 */
	AbPage getPage();
	
	/**
	 * 生成查询用的SQL，设置到查询参数中，并返回查询参数
	 * @return
	 */
	Map<String,Object> generateQuerySql();
	
	AbQueryFilter noPage();
	
	AbQueryFilter addFilter(String fildName,Object value ,ConditionType conditionType);

	//不处理下划线
	AbQueryFilter addFilterOriginal(String fildName,Object value ,ConditionType conditionType);

	//指定groupKey
	AbQueryFilter addFilter(String fildName,Object value ,ConditionType conditionType,String groupKey);

	
	AbQueryFilter likeFilter(String fildName,Object value );
	
	AbQueryFilter inFilter(String fildName,Object value );
	
	AbQueryFilter notEqFilter(String fildName,Object value );
	
	AbQueryFilter eqFilter(String fildName,Object value );
	
	AbQueryFilter addParam(String fildName, Object value);
	
	AbQueryFilter clearQuery();

	DefaultQueryFieldGroup getQueryFieldGroup();

	List<QuerySort> getQuerySortList();

	/**
	 * 获取查询列名，也就是 select xxxx from table
	 *
	 * @return 查询列名
	 */
	List<String> getSelectColumnNames();
}
