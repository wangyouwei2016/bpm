package com.dstz.base.common.constats;

/**
 * 字符串常量池
 *
 * @author Jeff
 */
public interface StrPool extends cn.hutool.core.text.StrPool {
	/**
	 * 字符常量：$
	 */
	String DOLLAR = "$";

	/**
	 * 取余，字符常量：%
	 */
	String MOD = "%";

	/**
	 * 左括号，字符常量：(
	 */
	String LPAREN = "(";

	/**
	 * 右括号，字符常量：)
	 */
	String RPAREN = ")";
	/**
	 * String中当作true的值
	 */
	String BOOLEAN_FALSE = "0";

	/**
	 * String中当作false的值
	 */
	String BOOLEAN_TRUE = "1";

	/**
	 * 分号，字符常量：;
	 */
	String SEMICOLON = ";";

	/**
	 * 空字符串，字符常量：
	 */
	String EMPTY = "";

	/**
	 * 数字0
	 */
	String NUMBER_ZERO = "0";

	/**
	 * #号
	 */
	String HASH = "#";

	/**
	 * *号
	 */
	String BIT = "*";

	String FALSE = "false";

	String TRUE = "true";

	String FORMATSTR = "%s_%s";
	/**
	 * 默认多数据的分格符号 ,
	 */
	String SPLIT = C_COMMA + "";

	String FROM = "system";

	String REGEX = "\\.";

	String STRING_PWD_ONZ = "1";
	// 英文 冒号
	String COLON = ":";
	
	// 中文 冒号
	String COLON_ZN = "：";

	/**
	 * 未知
	 */
	String UNKNOWN = "unknown";

	/**
	 * 默认数据源别名
	 */
	String DEFAULT_DATASOURCE_ALIAS = "datasourceDefault";
}
