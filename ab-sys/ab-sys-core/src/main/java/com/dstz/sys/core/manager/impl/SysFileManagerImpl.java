package com.dstz.sys.core.manager.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.property.PropertyEnum;
import com.dstz.base.common.utils.IdGeneratorUtils;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.common.utils.UserContextUtils;
import com.dstz.base.manager.impl.AbBaseManagerImpl;
import com.dstz.component.upload.api.IUploader;
import com.dstz.component.upload.api.UploaderFactory;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.constant.OnlineDocMethod;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.core.entity.SysFile;
import com.dstz.sys.core.entity.SysLogErr;
import com.dstz.sys.core.manager.SysFileManager;
import com.dstz.sys.core.mapper.SysFileMapper;
import com.dstz.sys.rest.model.dto.OnlineDocParam;
import com.dstz.sys.rest.model.dto.OnlineDocParamDTO;
import com.dstz.sys.rest.model.dto.OperateOnlineDocDTO;
import com.dstz.sys.rest.model.dto.UpdateFileDTO;
import com.dstz.sys.rest.model.vo.OnlineDocApiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 系统附件 通用服务实现类
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@Service("sysFileManager")
public class SysFileManagerImpl extends AbBaseManagerImpl<SysFile> implements SysFileManager {
    @Autowired
    SysFileMapper sysFileMapper;

    @Value(AbAppRestConstant.SYS_SERVICE_PREFIX)
    private String sysServicePrefix;

    @Override
    public SysFile upload(InputStream is, String fileName, String type) {
        String ext = "";
        if (fileName.contains(StrPool.DOT)) {
            ext = fileName.substring(fileName.lastIndexOf('.'));
        }
        String id = IdGeneratorUtils.nextId();

        // 1 先上传文件,以id为文件名保证不重复
        IUploader uploader = UploaderFactory.getDefault();
        String path = uploader.upload(is, id + ext, type);

        // 2 建立SysFile数据
        SysFile sysFile = new SysFile();
        sysFile.setId(id);
        sysFile.setName(fileName);
        sysFile.setUploader(uploader.type());
        sysFile.setPath(path);

        sysFile.setCreateOrgId(UserContextUtils.getGroupId());
        sysFile.setCreateBy(UserContextUtils.getUserId());
        sysFile.setTypeCode(type);
        create(sysFile);

        return sysFile;
    }

    @Override
    public int update(UpdateFileDTO updateDTO) {
        SysFile sysFile = getById(updateDTO.getFileId());
        if (sysFile == null) {
            throw new BusinessMessage(SysApiCodes.FILE_NOT_FOUND_ERROR.formatDefaultMessage(updateDTO.getFileId()));
        }

        String fileName = sysFile.getName();
        String ext = "";
        if (fileName.contains(StrPool.DOT)) {
            ext = fileName.substring(fileName.lastIndexOf('.'));
        }

        // 1 先上传文件,以id+时间戳 为文件名保证不重复
        IUploader uploader = UploaderFactory.getDefault();

        //以老的 id +时间创建新的文件 path
        String idDate = sysFile.getId() + "-" + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN);
        String path = uploader.upload(updateDTO.getFileStream(), idDate + ext, sysFile.getTypeCode());

        // 2 更新SysFile数据,只更新
        return super.update(null, Wrappers.lambdaUpdate(SysFile.class)
                .eq(SysFile::getId, sysFile.getId())
                .set(SysFile::getPath, path)
                .set(SysFile::getUpdateBy, updateDTO.getUserId())
                .set(SysFile::getUpdater, updateDTO.getUserName())
                .set(SysFile::getUpdateTime, new Date())
                .set(SysFile::getUploader, uploader.type()));
    }

    @Override
    public InputStream download(String fileId) {
        SysFile sysFile = sysFileMapper.selectById(fileId);
        IUploader uploader = UploaderFactory.getUploader(sysFile.getUploader());
        InputStream is = uploader.take(sysFile.getPath());
        if (is == null) {
            throw new BusinessMessage(SysApiCodes.FILE_NOT_FOUND_ERROR.formatDefaultMessage(sysFile.getName()));
        }
        return is;
    }

    @Override
    public void delete(String fileId) {
        SysFile sysFile = sysFileMapper.selectById(fileId);

        if (sysFile != null) {
            //做逻辑删除
            sysFile.setDelete(1);
            update(sysFile);
        }
    }

    private OnlineDocParamDTO buildOpenParam(OperateOnlineDocDTO operateDTO, String requestUrl) {
        SysFile file = super.getById(operateDTO.getFileId());
        if (file == null) {
            throw new BusinessMessage(SysApiCodes.FILE_NOT_FOUND_ERROR.formatDefaultMessage(operateDTO.getFileId()));
        }

        OnlineDocParam onlineDocParam = new OnlineDocParam();
        String sysFileServicePrefix = requestUrl + sysServicePrefix + "/sysFile";
        String downLoadPath = sysFileServicePrefix + "/download?fileId=";

        //文件信息
        onlineDocParam.setFileId(operateDTO.getFileId());
        String filePath = downLoadPath + operateDTO.getFileId();
        onlineDocParam.setFilePath(filePath);
        onlineDocParam.setFileName(file.getName());

        //用户信息
        IUser user = UserContextUtils.getValidUser();
        onlineDocParam.setUserName(user.getFullName());
        onlineDocParam.setUserId(user.getUserId());
        //onlineDocParam.setUserAvatar(user.g);

        onlineDocParam.setUserRight(operateDTO.getUserRight());
        onlineDocParam.setCallbackUrl(sysFileServicePrefix + "/update");
        onlineDocParam.setExtraData(operateDTO.getExtraData());
        onlineDocParam.setSaveFlag(operateDTO.getSaveFlag());
        onlineDocParam.setUserMenuPermission(operateDTO.getUserMenuPermission());

        return new OnlineDocParamDTO(OnlineDocMethod.OPEN, onlineDocParam);
    }

    private OnlineDocApiVO sendRequest(OnlineDocMethod method, OnlineDocParamDTO paramDTO) {
        String url = PropertyEnum.ONLINE_DOC_SERVICE_URL.getPropertyValue(String.class) + method.getPath();
        try (HttpResponse se = HttpUtil.createPost(url).form("jsonParams", JsonUtils.toJSONString(paramDTO)).execute()) {
            OnlineDocApiVO apiVO = JsonUtils.parseObject(se.body(), OnlineDocApiVO.class);
            if (apiVO.getErrorCode().equals(StrPool.BOOLEAN_FALSE)) {
                return apiVO;
            } else {
                throw new BusinessMessage(SysApiCodes.FILE_OPEN_FILE_ERROR.formatDefaultMessage(apiVO.getErrorMessage()));
            }
        }
    }


    @Override
    public String openFile(OperateOnlineDocDTO operateDTO, String requestUrl) {
        OnlineDocApiVO apiVO = sendRequest(OnlineDocMethod.OPEN, buildOpenParam(operateDTO, requestUrl));
        Map<String, String> result = (Map) apiVO.getResult();
        return result.get("urls");
    }

    @Override
    public Boolean closeFile(OperateOnlineDocDTO operateDTO) {
        OnlineDocParam param = new OnlineDocParam(operateDTO.getFileId());
        param.setSaveFlag(operateDTO.getSaveFlag());
        OnlineDocParamDTO paramDTO = new OnlineDocParamDTO(OnlineDocMethod.IS_OPEN, param);

        //先判断文档是否打开，已打开则关闭，否则不处理
        OnlineDocApiVO isOpenVO = sendRequest(OnlineDocMethod.IS_OPEN, paramDTO);
        if ((Boolean) isOpenVO.getResult()) {
            paramDTO.setMethod(OnlineDocMethod.CLOSE);
            OnlineDocApiVO closeVO = sendRequest(OnlineDocMethod.CLOSE, paramDTO);
            return (Boolean) closeVO.getResult();
        }
        return Boolean.TRUE;
    }
}
