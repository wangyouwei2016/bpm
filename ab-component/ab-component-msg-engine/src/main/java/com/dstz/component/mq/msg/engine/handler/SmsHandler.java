package com.dstz.component.mq.msg.engine.handler;

import com.dstz.base.common.identityconvert.IdentityConvert;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.component.mq.msg.engine.dto.MsgImplDTO;
import com.dstz.component.third.msg.engine.handler.AbsNotifyMessageHandler;
import com.dstz.org.api.model.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.dstz.component.mq.api.constants.JmsTypeEnum.SMS;

/**
 * 短消息发送处理器
 *
 * @author lightning
 */
@Component("smsHandler")
public class SmsHandler extends AbsNotifyMessageHandler<MsgImplDTO> {
    @Autowired
    IdentityConvert identityConvert;
    
	@Override
	public String getType() {
		return SMS.getType();
	}

	@Override
	public boolean sendMessage(MsgImplDTO message) {
		for (IUser user : identityConvert.convert2Users(message.getReceivers())) {
			System.err.print(JsonUtils.toJSONString(message));
			System.err.println(user.getAttrValue("mobile", String.class) + "请自行对接短信推送API");
			/*
			 * IAcsClient client = getIacsClient(); if (client == null ||
			 * StrUtil.isEmpty(user.getAttrValue("mobile", String.class))) {
			 * LOGGER.warn("IAcsClient is null or user mobile is empty"); continue; }
			 * //优先从普通文本框获取内容, 为空时再获取富文本 String text = message.getHtmlTemplate(); String
			 * content = text.replace(PARAM_USERNAME, user.getFullName());
			 * 
			 * CommonRequest smsRequest = getCommonRequest();
			 * 
			 * smsRequest.putQueryParameter("PhoneNumbers", user.getAttrValue("mobile",
			 * String.class));
			 * 
			 * setTemplateParam(smsRequest, user, content);
			 * 
			 * 
			 * try { CommonResponse response = client.getCommonResponse(smsRequest);
			 * JsonNode jsonNode = JsonUtils.toJSONNode(response.getData());
			 * 
			 * 
			 * if (!"OK".equals(jsonNode.get("Code"))) { LOGGER.error("发送短信失败:" +
			 * response.getData()); iAcsClient = null; } } catch (Exception e) { iAcsClient
			 * = null; LOGGER.error("发送短信失败" + e.getMessage(), e); }
			 */
		}
		return true;
	}

	/*    *//**
			 * 支持短信中自定义参数与模板。 否则使用默认模板 【 ${name}您好，您有新的任务${subject}，请及时查阅。】 {$subject}
			 * 即为消息体内容
			 *
			 * @param smsRequest
			 * @param user
			 * @param content
			 */

	/*
	 * private void setTemplateParam(CommonRequest smsRequest, IUser user, String
	 * content) {
	 * 
	 * if (content.startsWith("{")) { JsonNode templateParam =
	 * JsonUtils.toJSONNode(content); if (templateParam.has("templateCode")) {
	 * smsRequest.putQueryParameter("TemplateCode",
	 * templateParam.get("templateCode").toString()); }
	 * smsRequest.putQueryParameter("TemplateParam", content); return; } ObjectNode
	 * templateParam = JsonUtils.createObjectNode(); templateParam.put("name",
	 * user.getFullName()); templateParam.put("subject", content);
	 * smsRequest.putQueryParameter("TemplateParam",
	 * JsonUtils.toJSONString(templateParam));
	 * 
	 * }
	 * 
	 *//**
		 * 默认的请求
		 *
		 * @return
		 *//*
			 * private CommonRequest getCommonRequest() { String regionId =
			 * PropertyEnum.ALI_YUN_REGION_ID.getPropertyValue(String.class); String
			 * sysDomain = PropertyEnum.ALI_YUN_SYS_DOMAIN.getPropertyValue(String.class);
			 * String signName =
			 * PropertyEnum.ALI_YUN_SIGN_NAME.getPropertyValue(String.class); String
			 * templateCode =
			 * PropertyEnum.ALI_YUN_TEMPLATE_CODE.getPropertyValue(String.class);
			 * 
			 * CommonRequest request = new CommonRequest();
			 * request.setSysMethod(MethodType.POST); request.setSysDomain(sysDomain);
			 * request.setSysVersion("2017-05-25"); request.setSysAction("SendSms");
			 * request.putQueryParameter("RegionId", regionId);
			 * 
			 * request.putQueryParameter("SignName", signName);
			 * request.putQueryParameter("TemplateCode", templateCode);
			 * 
			 * return request; }
			 * 
			 * IAcsClient iAcsClient = null;
			 * 
			 * private IAcsClient getIacsClient() { if (iAcsClient != null) { return
			 * iAcsClient; }
			 * 
			 * String regionId =
			 * PropertyEnum.ALI_YUN_REGION_ID.getPropertyValue(String.class); String
			 * accessKeyId =
			 * PropertyEnum.ALI_YUN_ACCESS_KEY_ID.getPropertyValue(String.class); String
			 * accessSecret =
			 * PropertyEnum.ALI_YUN_ACCESS_SECRET.getPropertyValue(String.class); if
			 * (StrUtil.isEmpty(accessKeyId) || StrUtil.isEmpty(accessSecret)) { LOGGER.
			 * warn("Not configured system property aliyun_accessKeyId、aliyun_accessSecret"
			 * ); return null; }
			 * 
			 * DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId,
			 * accessSecret); IAcsClient client = new DefaultAcsClient(profile);
			 * this.iAcsClient = client; return client; }
			 */

	@Override
	public String getTitle() {
		return "短信";
	}

	@Override
	public boolean getIsDefault() {
		return false;
	}

	@Override
	public boolean getSupportHtml() {
		return true;
	}

}
