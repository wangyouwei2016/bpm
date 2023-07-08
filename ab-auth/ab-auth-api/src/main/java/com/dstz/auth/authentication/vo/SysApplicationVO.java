package com.dstz.auth.authentication.vo;

/**
 * 系统应用
 *
 * @author wacxhs
 */
public class SysApplicationVO implements java.io.Serializable {

	private static final long serialVersionUID = 5700878947689459575L;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 密匙
	 */
	private String secret;

	/**
	 * 授权范围
	 */
	private String scope;

	/**
	 * 资源集合
	 */
	private String resourceIds;

	/**
	 * 授权类型
	 */
	private String grantTypes;

	/**
	 * 回调地址
	 */
	private String redirectUri;

	/**
	 * 自动授权
	 */
	private Integer autoapprove;

	/**
	 * 有效期
	 */
	private Integer accessTokenValidity;

	/**
	 * 刷新秒数
	 */
	private Integer refreshTokenValidity;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Integer getAutoapprove() {
		return autoapprove;
	}

	public void setAutoapprove(Integer autoapprove) {
		this.autoapprove = autoapprove;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
}
