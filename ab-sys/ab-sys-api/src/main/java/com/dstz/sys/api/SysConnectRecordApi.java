package com.dstz.sys.api;

import com.dstz.sys.api.dto.SysConnectRecordDTO;
import com.dstz.sys.api.vo.SysConnectRecordVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jinxia.hou
 * @Name SysConnectRecordApi
 * @description: 公共业务关联记录 接口
 * @date 2022/3/815:43
 */
@Validated
public interface SysConnectRecordApi {

    /**
     * 通过 targetId 获取关联源
     *
     * @param targetId
     * @param type
     * @return
     */
    List<SysConnectRecordVO> getByTargetId(String targetId, String type);

    /**
     * 批量保存
     *
     * @param records
     */
    void save(@Valid List<SysConnectRecordDTO> records);

    /**
     * 批量保存
     *
     * @param records
     */
    void save(@Valid SysConnectRecordDTO records);

    /**
     * 通过sourceId 删除
     *
     * @param type
     * @param id
     */
    void removeBySourceId(String id, String type);

    /**
     * 检查是否被关联
     *
     * @param targetId
     * @param type
     */
    void checkIsRelatedWithBusinessMessage(String targetId, String type);
}
