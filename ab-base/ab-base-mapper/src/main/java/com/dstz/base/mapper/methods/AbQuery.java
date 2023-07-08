package com.dstz.base.mapper.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.text.MessageFormat;
import java.util.Map;

public class AbQuery extends AbstractMethod {
	public AbQuery() {
		super("query");
	}

	public AbQuery(String name) {
		super(name);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		StringBuilder sb = new StringBuilder("<script>SELECT ");
		// 填充字段列
		sb.append("<choose>")
		  .append("<when test=\"@cn.hutool.core.util.StrUtil@isNotBlank(SELECT_COLUMN_NAMES)\">${SELECT_COLUMN_NAMES}</when>")
		  .append(MessageFormat.format("<otherwise>{0}</otherwise>", tableInfo.getAllSqlSelect()));
		sb.append("</choose>");
		
		//拼装sql中，记得用<script>包围才能运行<if>等标签
		sb.append(" FROM ").append(tableInfo.getTableName()).append(" ");
		// 查询条件
		sb.append("<where><if test=\"@cn.hutool.core.util.StrUtil@isNotBlank(whereSql)\">${whereSql}</if></where>");
		// 排序
		sb.append(" ORDER BY  ");
		sb.append("<choose>")
		  .append("<when test=\"@cn.hutool.core.util.StrUtil@isNotBlank(orderBySql)\">${orderBySql}</when>")
		  .append(MessageFormat.format("<otherwise>{0} DESC</otherwise>", tableInfo.getKeyColumn()));
		sb.append("</choose>");
		  
		sb.append("</script>");

		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sb.toString(), Map.class);
		//注意addSelectMappedStatementForTable是查询用的，新增、更新、删除都分别对应不同方法
		return this.addSelectMappedStatementForTable(mapperClass, "query", sqlSource, tableInfo);
	}

}
