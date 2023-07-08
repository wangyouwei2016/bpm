package com.dstz.base.mapper.methods;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.enums.AbDbType;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 批量创建
 *
 * @author wacxhs
 */
public class AbBulkCreate extends AbstractMethod {

	private static final long serialVersionUID = 116839614956393180L;

	public static final String METHOD_NAME = "bulkCreate";

	public static final String METHOD_PARAM_NAME = "list";


	public AbBulkCreate() {
		super(METHOD_NAME);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sqlScript;
		if(AbDbType.MYSQL.getDb().equalsIgnoreCase(configuration.getDatabaseId()) || StrUtil.isEmpty(configuration.getDatabaseId())){
			sqlScript = injectMappedStatementByMysql(mapperClass, modelClass, tableInfo);
		}else{
			throw new IllegalStateException(String.format("Not Support databaseId %s", configuration.getDatabaseId()));
		}
		SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sqlScript, modelClass);
		return this.addInsertMappedStatement(mapperClass, modelClass, METHOD_NAME, sqlSource, NoKeyGenerator.INSTANCE, null, null);
	}

	private String injectMappedStatementByMysql(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String insertSqlColumn = tableInfo.getAllSqlSelect();
		String insertSqlProperty = StrUtil.removeSuffix(tableInfo.getKeyInsertSqlProperty(true, "et.", false) + filterTableFieldInfo(tableInfo.getFieldList(), null, tableFieldInfo -> tableFieldInfo.getInsertSqlProperty("et."), StrUtil.EMPTY), StrUtil.COMMA);
		String convertForeach = SqlScriptUtils.convertForeach(StrUtil.wrap(insertSqlProperty, StrPool.LPAREN, StrPool.RPAREN), METHOD_PARAM_NAME, null, "et", StrUtil.COMMA);
		return String.format(SqlMethod.INSERT_ONE.getSql(), tableInfo.getTableName(), StrUtil.wrap(insertSqlColumn, StrPool.LPAREN, StrPool.RPAREN), convertForeach);
	}
}
