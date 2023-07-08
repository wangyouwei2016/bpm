package com.dstz.base.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * jdbc 工具类
 *
 * @author wacxhs
 */
public class JdbcUtils {

    /**
     * 获取连接 schema
     *
     * @return 连接 schema
     */
    public static String getConnectionSchema(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            return connection.getSchema();
        } catch (SQLException e) {
            ExceptionUtil.wrapAndThrow(e);
            return null;
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

    /**
     * 获取连接产品名称
     *
     * @param dataSource 数据源
     * @return 连接产品名称
     */
    public static String getConnectionProductName(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            return connection.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            ExceptionUtil.wrapAndThrow(e);
            return null;
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }
}
