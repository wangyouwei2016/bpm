package com.dstz.sys.api;

import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.api.dto.SysConnectRecordDTO;
import com.dstz.sys.api.vo.SysConnectRecordVO;
import com.dstz.sys.core.entity.SysConnectRecord;
import com.dstz.sys.core.manager.SysConnectRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jinxia.hou
 * @Name SysConnectRecordServiceImpl
 * @description: 公共业务关联记录 接口实现
 * @date 2022/3/815:48
 */

@Service
@Validated
public class SysConnectRecordApiImpl implements SysConnectRecordApi {
    public SysConnectRecordApiImpl(SysConnectRecordManager connectRecordManager) {
        this.connectRecordManager = connectRecordManager;
    }

    private final SysConnectRecordManager connectRecordManager;

    @Override
    public List<SysConnectRecordVO> getByTargetId(String id, String type) {
        List<SysConnectRecord> list = connectRecordManager.getByTargetId(id, type);
        return BeanCopierUtils.transformList(list, SysConnectRecordVO.class);
    }

    @Override
    public void save(@Valid List<SysConnectRecordDTO> records) {
        List<SysConnectRecord> recordsList = BeanCopierUtils.transformList(records, SysConnectRecord.class);
        connectRecordManager.bulkCreate(recordsList);
    }

    @Override

    public void save(@Valid SysConnectRecordDTO records) {
        connectRecordManager.create(BeanCopierUtils.transformBean(records, SysConnectRecord.class));
    }


    @Override
    public void removeBySourceId(String sourceId, String type) {
        connectRecordManager.removeBySourceId(sourceId, type);
    }

    @Override
    public void checkIsRelatedWithBusinessMessage(String targetId, String type) {
        List<SysConnectRecord> list = connectRecordManager.getByTargetId(targetId, type);
        if (list.isEmpty()) {
            return;
        }

        Set<String> notices = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        list.forEach(record -> {
            // 排下重
            if (!notices.contains(record.getNotice())) {
                if (sb.length() > 0) {
                    sb.append("<br/>");
                }
                sb.append(record.getNotice());
                notices.add(record.getNotice());
            }

        });

        throw new BusinessMessage(SysApiCodes.CONNECT_RECORD_ERROR.formatDefaultMessage(sb.toString()));
    }
}
