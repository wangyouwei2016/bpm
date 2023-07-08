package com.dstz.sys.core.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.sys.core.entity.SysConnectRecord;
import com.dstz.sys.core.manager.SysConnectRecordManager;
import com.dstz.sys.core.mapper.SysConnectRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公共业务关联记录 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Service("sysConnectRecordManager")
public class SysConnectRecordManagerImpl extends AbBaseManagerImpl<SysConnectRecord> implements SysConnectRecordManager {

    private final SysConnectRecordMapper sysConnectRecordMapper;

    public SysConnectRecordManagerImpl(SysConnectRecordMapper sysConnectRecordMapper) {
        this.sysConnectRecordMapper = sysConnectRecordMapper;
    }

    @Override
    public List<SysConnectRecord> getByTargetId(String targetId, String type) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("target_id_", targetId);
        if (StrUtil.isNotEmpty(type)) {
            queryWrapper.eq("type_", type);
        }
        return sysConnectRecordMapper.selectList(queryWrapper);
    }

    @Override
    public void removeBySourceId(String sourceId, String type) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("source_id_", sourceId);
        if (StrUtil.isNotEmpty(type)) {
            queryWrapper.eq("type_", type);
        }
        sysConnectRecordMapper.delete(queryWrapper);
    }
}
