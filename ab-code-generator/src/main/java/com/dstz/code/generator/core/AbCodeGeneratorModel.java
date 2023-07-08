package com.dstz.code.generator.core;

import cn.hutool.core.util.StrUtil;

/**
 * 生成器参数对象
 *
 * @author wacxhs
 */
public class AbCodeGeneratorModel {

	/**
	 * 输出目录
	 */
	private String outputDir;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 包名
	 */
	private String packageName;

	/**
	 * 指定表，支持通配符
	 */
	private String[] includeTable;

	/**
	 * 排除表，支持通配符
	 */
	private String[] excludeTable;

	public String getOutputDir() {
		return StrUtil.blankToDefault(outputDir, Constant.DEFAULT_OUTPUT_DIR);
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getAuthor() {
		return StrUtil.blankToDefault(author, System.getProperty("user.name"));
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String[] getIncludeTable() {
		return includeTable;
	}

	public void setIncludeTable(String[] includeTable) {
		this.includeTable = includeTable;
	}

	public String[] getExcludeTable() {
		return excludeTable;
	}

	public void setExcludeTable(String[] excludeTable) {
		this.excludeTable = excludeTable;
	}
}
