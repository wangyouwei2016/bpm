package com.dstz.component.uploader.ordinary;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.component.upload.api.IUploader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * <pre>
 * 描述：普通的上传器
 * 上传到服务器的某个文件夹中
 * 每次上传时会自动放在当前日期yyyyMMdd的目录下
 * </pre>
 *
 * @author lightning
 */
public class OrdinaryUploader implements IUploader {

    @Override
    public String type() {
        return "ordinary";
    }

    @Autowired
    private OrdinaryProperties ordinaryProperties;


    @Override
    public String upload(InputStream is, String name, String type) {
        FileUtil.writeFromStream(is, getPath(name, type));
        return getPath(name, type);
    }

    @Override
    public InputStream take(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (Exception e) {
            throw new BusinessException(GlobalApiCodes.INTERNAL_ERROR.formatDefaultMessage(e));
        }
    }

    @Override
    public void remove(String path) {
        FileUtil.del(path);
    }

    private String getPath(String name, String type) {
        return ordinaryProperties.getPath() + File.separator + type + File.separator + DateUtil.format(new Date(), "yyyyMMdd") + File.separator + name;
    }

}
