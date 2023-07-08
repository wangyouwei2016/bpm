package com.dstz.auth.model.dto;
/**
 *     微信消息体
 *
 * @author lightning
 *
 */
public class WxMsgDTO {
	// OPENID
	private String touser ;
	//更多消息类型请参考微信官方文档，这里默认只发文本消息  https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html
	private String msgtype = "textcard";
	private WxTextContext wxTextContext;
	private WxTextCardContext textcard;
	private Integer agentid = null;
	
	public WxMsgDTO() {
		
	}

	public WxMsgDTO(String openid,String content) {
		WxTextContext wxContent = new WxTextContext(content);
		this.wxTextContext = wxContent;
		this.touser = openid;
	}
	
	public WxMsgDTO(String openid,String title,String description ,String url) {
		WxTextCardContext wxContent = new WxTextCardContext(title,description,url);
		this.textcard = wxContent;
		this.touser = openid;
	}
	
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public WxTextContext getWxTextContext() {
		return wxTextContext;
	}

	public void setWxTextContext(WxTextContext wxTextContext) {
		this.wxTextContext = wxTextContext;
	}

	public WxTextCardContext getTextcard() {
		return textcard;
	}

	public void setTextcard(WxTextCardContext textcard) {
		this.textcard = textcard;
	}

	public Integer getAgentid() {
		return agentid;
	}

	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}

}
