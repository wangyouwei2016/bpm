package com.dstz.base.common.identityconvert;

import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.enums.IdentityType;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;

import java.io.Serializable;

/**
 * 描述：流程与组织挂接实体接口 type : user / 其他group 类型
 *
 * @author jeff
 */
public class SysIdentity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;

	public SysIdentity() {
	}

	public SysIdentity(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public SysIdentity(IUser user) {
		this.id = user.getUserId();
		this.name = user.getFullName();
		this.type = IdentityType.USER.getKey();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public int hashCode() {
		return this.id.hashCode() + this.type.hashCode();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SysIdentity)) {
			return false;
		}

		if (StrUtil.isEmpty(id) || StrUtil.isEmpty(name)) {
			return false;
		}

		SysIdentity identity = (SysIdentity) obj;

		if (this.type.equals(identity.getType()) && this.id.equals(identity.getId())) {
			return true;
		}

		return false;
	}

	/**
	 * 从group创建SysIdentity实例
	 *
	 * @param group group
	 * @return SysIdentity 实例
	 */
	public static SysIdentity of(IGroup group) {
		return new SysIdentity(group.getGroupId(), group.getGroupName(), group.getGroupType());
	}

	/**
	 * <pre>
	 * 返回封装了type和name的显示用的信息
	 * </pre>	
	 * @return
	 */
	public String getAssign() {
		String str = "(";
		if ("role".equals(type)) {
			str += "角色";
		} else if ("org".equals(type)) {
			str += "组织";
		} else if ("group".equals(type)) {
			str += "小组";
		} else if ("post".equals(type)) {
			str += "岗位";
		} else if ("user".equals(type)) {
			str += "用户";
		}
		return str + ")" + name;
	}

}
