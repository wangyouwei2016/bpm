package com.dstz.springboot.autoconfigure.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 鉴权配置
 * @author lightning
 */
@ConfigurationProperties(prefix = "ab.security")
public class AbSecurityProperties {

	/**
	 * 逗号分隔
	*/

	/**忽略xss 的地址 **/
	private String xssIngores = "";
	/**忽略跨域访问 的地址 **/
	private String [] csrfIngores = new String[]{"127.0.0.1"};
	/**忽略鉴权 的地址 **/
	private String [] authIngores = new String[]{"/login.*"};

    /**
     * 最大会话数
     * <ul>
     *     <li>-1：表示会话不受任何限制</li>
     *     <li>0：限制除登陆用户最后一次登陆会话，其它会话都失效</li>
     *     <li>&gt;0：限制用户下最大会话数</li>
     * </ul>
     */
    private Integer maximumSessions = -1;

	/**
	 * 启用跨域拦截
	 */
	private Boolean enableCors = Boolean.TRUE;

	/**
	 * 启用跨站拦截
	 */
	private Boolean enableCsrf = Boolean.TRUE;

	public String getXssIngores() {
		return xssIngores;
	}
	public void setXssIngores(String xssIngores) {
		this.xssIngores = xssIngores;
	}

	public String[] getCsrfIngores() {
		return csrfIngores;
	}

	public void setCsrfIngores(String[] csrfIngores) {
		this.csrfIngores = csrfIngores;
	}

	public String[] getAuthIngores() {
		return authIngores;
	}

	public void setAuthIngores(String[] authIngores) {
		this.authIngores = authIngores;
	}

	public Integer getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(Integer maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

	public Boolean getEnableCors() {
		return enableCors;
	}

	public void setEnableCors(Boolean enableCors) {
		this.enableCors = enableCors;
	}

	public Boolean getEnableCsrf() {
		return enableCsrf;
	}

	public void setEnableCsrf(Boolean enableCsrf) {
		this.enableCsrf = enableCsrf;
	}
}
