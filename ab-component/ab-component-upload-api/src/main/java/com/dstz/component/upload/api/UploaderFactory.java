package com.dstz.component.upload.api;

import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.BusinessException;
import com.dstz.base.common.property.PropertyEnum;

import java.util.Map;
import java.util.Map.Entry;

/**
 * <pre>
 * 描述：上传器工厂
 * </pre>
 *
 * @author lightning
 */
public class UploaderFactory {
    private UploaderFactory() {

    }

    /**
     * <pre>
     * 获取上传器
     * </pre>
     *
     * @param type
     * @return
     */
    public static IUploader getUploader(String type) {
        Map<String, IUploader> map = SpringUtil.getBeansOfType(IUploader.class);
        for (Entry<String, IUploader> entry : map.entrySet()) {
            if (entry.getValue().type().equals(type)) {
                return entry.getValue();
            }
        }
        throw new BusinessException(GlobalApiCodes.INTERNAL_ERROR.formatMessage("找不到类型[{}]的上传器的实现类", type));
    }

    /**
     * <pre>
     * 返回默认的上传器
     * </pre>
     *
     * @return
     */
    public static IUploader getDefault() {
        return getUploader(PropertyEnum.UPLOADER_DEFAULT.getYamlValue(String.class));
    }
}
