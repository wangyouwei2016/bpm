package com.dstz.sys.rest.model.vo;

/**
 * 自动翻译 VO
 *
 * @author wacxhs
 */
public class AutoTranslateVO implements java.io.Serializable {

	private static final long serialVersionUID = -1414897543240472067L;

	/**
	 * 失败文本
	 */
	private String errorMsg;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 翻译后的文本
	 */
	private String dstText;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDstText() {
		return dstText;
	}

	public void setDstText(String dstText) {
		this.dstText = dstText;
	}
}
