package com.dstz.auth.authentication.api.constant;

/**
 * 组织级别
 *
 * @author lightning
 */
public enum ResouceTypeConstant {
    /**
     * 菜单
     */
    MENU("menu", "菜单"),
    /**
     * 链接
     */
    LINK("link", "链接"),
    /**
     * 按钮
     */
    BUTTON("button", "按钮");

    private final String key;
    private final String label;


    ResouceTypeConstant(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getLabel() {
  		return label;
  	}

	public String getKey() {
		return key;
	}
}
