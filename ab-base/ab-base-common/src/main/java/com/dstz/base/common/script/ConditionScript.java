package com.dstz.base.common.script;

import cn.hutool.core.util.StrUtil;

/**
 * <pre>
 * 前端通用条件脚本配置对象
 * </pre>
 * 
 * @author aschs
 * @date 2022年6月23日
 * @owner 深圳市大世同舟信息科技有限公司
 */
public class ConditionScript implements java.io.Serializable{

	private static final long serialVersionUID = 8232653926713818605L;

	/**
	 * 脚本类型 ScriptType
	 */
	private String type;
	/**
	 * 手写脚本内容
	 */
	private HandScript handScript;
	/**
	 * 配置脚本内容
	 */
	private ConfigScript configScript;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HandScript getHandScript() {
		return handScript;
	}

	public void setHandScript(HandScript handScript) {
		this.handScript = handScript;
	}

	public ConfigScript getConfigScript() {
		return configScript;
	}

	public void setConfigScript(ConfigScript configScript) {
		this.configScript = configScript;
	}
	
	/**
	 * <pre>
	 * 根据类型获取结果脚本
	 * </pre>	
	 * @return
	 */
	public String getResultScript() {
		if (ScriptType.HAND.equalsWithKey(this.type)) {
			return this.handScript.getScript();
		}
		if (ScriptType.CONFIG.equalsWithKey(this.type)) {
			return this.configScript.getScript();
		}
		return "";
	}
	
	/**
	 * <pre>
	 * 根据类型获取结果描述
	 * </pre>	
	 * @return
	 */
	public String getResultDesc() {
		String desc = null;
		if (ScriptType.HAND.equalsWithKey(this.type)) {
			desc = this.handScript.getDesc();
		}
		if (ScriptType.CONFIG.equalsWithKey(this.type)) {
			desc = this.configScript.getDesc();
		}
		if (StrUtil.isEmpty(desc)) {
			return this.getResultScript();
		}
		return desc;
	}
}
