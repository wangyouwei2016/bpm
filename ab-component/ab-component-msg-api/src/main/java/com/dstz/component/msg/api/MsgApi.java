package com.dstz.component.msg.api;

import cn.hutool.core.util.ObjectUtil;
import com.dstz.component.msg.api.dto.MsgDTO;

import java.util.List;

/**
 * 消息发送实现
 *
 * @author lightning
 */
public interface MsgApi {

    /**
     * 发送消息
     *
     * @param msgDTO
     */
    void sendMsg(MsgDTO msgDTO);

    /**
     * 批量发送消息
     *
     * @param msgDTOList
     */
    void sendMsg(List<MsgDTO> msgDTOList);


    /**
     * 模板转换
     * eg 把  请审阅 ${myParam}  转换为 请审阅 xxx请假申请
     * @param templateStr 要解析的字符串
     * @param templateCode 模板编码
     * @param obj 系统参数
     * @return
     */
    String convertTemplateStr(String templateStr, String templateCode, Object obj);
}
