package com.dstz.springboot.autoconfigure.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 客户端信息传输配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.rest-client-info-transform")
public class AbRestClientInfoTransformProperties {

	/**
	 * Cookie
	 */
	private List<NameValue> cookies;

	/**
	 * header
	 */
	private List<NameValue> headers;

	public List<NameValue> getCookies() {
		return cookies;
	}

	public void setCookies(List<NameValue> cookies) {
		this.cookies = cookies;
	}

	public List<NameValue> getHeaders() {
		return headers;
	}

	public void setHeaders(List<NameValue> headers) {
		this.headers = headers;
	}

	public static final class NameValue {
		/**
		 * 属性名称
		 */
		private String name;
		/**
		 * 属性值，为空则从当前请求中取
		 */
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
