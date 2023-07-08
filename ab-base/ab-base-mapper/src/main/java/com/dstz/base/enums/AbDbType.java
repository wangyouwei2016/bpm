package com.dstz.base.enums;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.property.PropertyEnum;
import com.dstz.base.utils.JdbcUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 数据库类型
 *
 * @author wacxhs
 */
public enum AbDbType {

    /**
     * MYSQL
     */
    MYSQL("MySQL", "mysql", "MySQL数据库"),

    /**
     * ORACLE
     */
    ORACLE("Oracle", "oracle", "Oracle数据库"),

    /**
     * SQLSERVER
     */
    SQLSERVER("Microsoft SQL Server", "sqlserver", "SQLServer数据库"),

    /**
     * POSTGRE
     */
    POSTGRESQL("PostgreSQL", "postgresql", "Postgre数据库"),

    /**
     * Kingbase
     */
    KINGBASE("kingbasees", "kingbasees", "人大金仓数据库"),

    /**
     * 达梦数据库
     */
    DM("DM DBMS", "DM", "达梦数据库"),
    
    /**
     * 未知数据库
     */
    UNKNOW(null, "UNKNOW", "未知数据库");

    /**
     * 数据库厂商名称
     */
    private final String productName;

    /**
     * 数据库名称
     */
    private final String db;

    /**
     * 数据库描述
     */
    private final String desc;

    AbDbType(String productName, String db, String desc) {
        this.productName = productName;
        this.db = db;
        this.desc = desc;
    }

    public String getProductName() {
        return productName;
    }

    public String getDb() {
        return db;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取数据库类型
     *
     * @param dbType 字符串数据库类型
     * @return 数据库类型
     */
    public static AbDbType getDbType(String dbType) {
        return Arrays.stream(values()).filter(item -> item.getDb().equalsIgnoreCase(dbType)).findFirst().orElse(AbDbType.UNKNOW);

    }

    boolean matchProductName(String productName) {
        return this.productName != null && this.productName.equalsIgnoreCase(productName);
    }

    public boolean equalsAny(AbDbType... any) {
        for (AbDbType target : any) {
            if (equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据库厂商名称获取数据库类型
     * @param productName product name
     * @return the database driver or {@link #UNKNOW} if not found
     */
    public static AbDbType fromProductName(String productName) {
        if (StringUtils.hasLength(productName)) {
            for (AbDbType candidate : values()) {
                if (candidate.matchProductName(productName)) {
                    return candidate;
                }
            }
        }
        return UNKNOW;
    }

    /**
     * 当前数据库类型
     *
     * @return 数据库类型
     */
    public static AbDbType currentDbType() {
        String jdbcType = PropertyEnum.JDBC_TYPE.getYamlValue(String.class);
        if (CharSequenceUtil.isEmpty(jdbcType)) {
            synchronized (AbDbType.class) {
                jdbcType = PropertyEnum.JDBC_TYPE.getYamlValue(String.class);
                if (CharSequenceUtil.isEmpty(jdbcType)) {
                    DataSource dataSource = SpringUtil.getBean(DataSource.class);
                    jdbcType = JdbcUtils.getConnectionProductName(dataSource);
                    Assert.notBlank(jdbcType, "无法从数据源连接中获取到数据库类型，您可通过环境变量({})指定", PropertyEnum.JDBC_TYPE.getKey());
                    System.setProperty(PropertyEnum.JDBC_TYPE.getKey(), jdbcType);
                }
            }
        }
        return getDbType(jdbcType);
    }
}
