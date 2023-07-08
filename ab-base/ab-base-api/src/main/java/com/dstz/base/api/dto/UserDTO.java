package com.dstz.base.api.dto;

/**
 * 用户数据
 *
 * @author wacxhs
 */
public class UserDTO implements java.io.Serializable {

	private static final long serialVersionUID = 687720125614093757L;

	/**
	 * 用户ID
	 */
	private String id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户姓名
	 */
	private String fullName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", fullName='" + fullName + '\'' +
				'}';
	}
}
