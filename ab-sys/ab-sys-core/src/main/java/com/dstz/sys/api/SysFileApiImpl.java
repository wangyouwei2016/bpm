package com.dstz.sys.api;

import cn.hutool.core.util.ArrayUtil;
import com.dstz.base.common.utils.BeanCopierUtils;
import com.dstz.sys.api.dto.SysFileDTO;
import com.dstz.sys.core.entity.SysFile;
import com.dstz.sys.core.manager.SysFileManager;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author jinxia.hou
 * @Name SysFileServiceImpl
 * @description: 文件附件服务
 * @date 2022/2/249:38
 */
@Service
public class SysFileApiImpl implements SysFileApi {
    private final SysFileManager sysFileManager;

    public SysFileApiImpl(SysFileManager sysFileManager) {
        this.sysFileManager = sysFileManager;
    }

    @Override
    public SysFileDTO upload(InputStream is, String fileName) {
        SysFile file = sysFileManager.upload(is, fileName, null);
        return BeanCopierUtils.transformBean(file, SysFileDTO.class);
    }

    @Override
    public InputStream download(String fileId) {
        return sysFileManager.download(fileId);
    }

    @Override
    public void delete(String... fileId) {
        if (ArrayUtil.isEmpty(fileId)) {
            return;
        }
        for (String id : fileId) {
            sysFileManager.delete(id);
        }
    }

    @Override
    public SysFileDTO getById(String fileId) {
        return BeanCopierUtils.transformBean(getById(fileId), SysFileDTO.class);
    }
}
