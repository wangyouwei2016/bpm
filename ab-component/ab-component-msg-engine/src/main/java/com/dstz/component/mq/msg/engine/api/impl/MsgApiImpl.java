package com.dstz.component.mq.msg.engine.api.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.freemark.impl.FreemarkerEngine;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.component.mq.api.model.DefaultJmsDTO;
import com.dstz.component.mq.api.model.JmsDTO;
import com.dstz.component.mq.api.producer.JmsProducer;
import com.dstz.component.mq.msg.engine.dto.MsgImplDTO;
import com.dstz.component.msg.api.MessageTemplateApi;
import com.dstz.component.msg.api.MsgApi;
import com.dstz.component.msg.api.dto.MsgDTO;
import com.dstz.component.msg.api.vo.CardTemplateData;
import com.dstz.component.msg.api.vo.MessageTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.dstz.component.mq.msg.engine.constants.MsgEngineStatusCode.PARAM_TEMPLATE_CODE_MISS;

/**
 * 业务消息实现
 *
 * @author lightning
 */
@Service
public class MsgApiImpl implements MsgApi {

    @Autowired
    JmsProducer jmsProducer;

    @Autowired
    MessageTemplateApi messageTemplateApi;

    @Autowired
    FreemarkerEngine freemarkerEngine;

    private Pattern TMP_PARAM_REGEX = Pattern.compile(".*\\$\\{.*\\}.*");

    /**
     * 单条发送
     *
     * @param msgDTO
     */
    @Override
    public void sendMsg(MsgDTO msgDTO) {
        if (CollUtil.isNotEmpty(msgDTO.getReceivers())) {
            jmsProducer.sendToQueue(dealMsgDTO(msgDTO));
        }
    }

    /**
     * 批量发送
     *
     * @param msgDTOList
     */
    @Override
    public void sendMsg(List<MsgDTO> msgDTOList) {
        List<JmsDTO> jmsDTOList = CollUtil.newArrayList();
        msgDTOList.forEach(e -> {
            if (CollUtil.isNotEmpty(e.getReceivers())) {
                jmsDTOList.addAll(dealMsgDTO(e));
            }
        });
        jmsProducer.sendToQueue(jmsDTOList);
    }

    public String convertTemplateParam(String templateParam, Object object) {
        if (StrUtil.isNotBlank(templateParam) && ReUtil.isMatch(TMP_PARAM_REGEX, templateParam)) {
            return freemarkerEngine.parseByString(templateParam, object);
        }
        return templateParam;
    }

    public String convertTemplateStr(String templateStr, String paramStr) {
        if (StrUtil.isNotBlank(templateStr) && ReUtil.isMatch(TMP_PARAM_REGEX, templateStr)) {
            Map<String, String> paramMap = JsonUtils.parseObject(paramStr, Map.class);
            return freemarkerEngine.parseByString(templateStr, paramMap);
        }
        return templateStr;
    }

    @Override
    public String convertTemplateStr(String templateStr, String templateCode, Object obj) {
        MessageTemplateVO messageTemplateVO = messageTemplateApi.getTemplateByCode(templateCode);
        if (StrUtil.isNotBlank(messageTemplateVO.getTemplateParamJson()) && ReUtil.isMatch(TMP_PARAM_REGEX, messageTemplateVO.getTemplateParamJson())) {
            return convertTemplateStr(templateStr, convertTemplateParam(messageTemplateVO.getTemplateParamJson(), obj));
        }
        return templateStr;
    }

    /**
     * 处理请求参数
     *
     * @param msgDTO
     * @return
     */
    private List<JmsDTO> dealMsgDTO(MsgDTO msgDTO) {
        MessageTemplateVO messageTemplateVO = messageTemplateApi.getTemplateByCode(msgDTO.getTemplateCode());
        String htmlTemplate = messageTemplateVO.getHtmlTemplate();
        String cardTemplate = messageTemplateVO.getCardTemplate();
        //格式化用户自定义参数
        String paramJson = this.convertTemplateParam(messageTemplateVO.getTemplateParamJson(), msgDTO.getObject());
        Map<String, String> paramMap = MapUtil.newHashMap();

        if (StrUtil.isNotBlank(paramJson)) {
            paramMap = JsonUtils.parseObject(paramJson, Map.class);
            //格式化模板消息
            if (StrUtil.isNotBlank(htmlTemplate) && ReUtil.isMatch(TMP_PARAM_REGEX, htmlTemplate)) {
                htmlTemplate = freemarkerEngine.parseByString(messageTemplateVO.getHtmlTemplate(), paramMap);
            }
            if (StrUtil.isNotBlank(cardTemplate) && ReUtil.isMatch(TMP_PARAM_REGEX, cardTemplate)) {
                cardTemplate = freemarkerEngine.parseByString(messageTemplateVO.getCardTemplate(), paramMap);
            }
            //格式化标题
            if (StrUtil.isNotBlank(msgDTO.getSubject()) && ReUtil.isMatch(TMP_PARAM_REGEX, msgDTO.getSubject())) {
                msgDTO.setSubject(freemarkerEngine.parseByString(msgDTO.getSubject(), paramMap));
            }
        }
        CardTemplateData cardTemplateData = StrUtil.isNotBlank(cardTemplate) ? JsonUtils.parseObject(cardTemplate, CardTemplateData.class) : new CardTemplateData();
        MsgImplDTO msgImplDTO = new MsgImplDTO(msgDTO.getSubject(), paramMap, htmlTemplate, cardTemplateData, msgDTO.getReceivers(), msgDTO.getBusinessId(), msgDTO.getInnerMsgType(), msgDTO.getExtendVars());
        List<JmsDTO> msgDTOList = new LinkedList<>();
        msgDTO.getMsgType().forEach(e -> msgDTOList.add(new DefaultJmsDTO<>(e, msgImplDTO)));
        return msgDTOList;
    }
}
