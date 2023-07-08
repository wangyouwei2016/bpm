package com.dstz.sys.rest.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.constats.StrPool;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.base.common.utils.AbRequestUtils;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.sys.api.constant.SysApiCodes;
import com.dstz.sys.core.entity.SysFile;
import com.dstz.sys.core.manager.SysFileManager;
import com.dstz.sys.rest.model.dto.OnlineDocParam;
import com.dstz.sys.rest.model.dto.OnlineDocParamDTO;
import com.dstz.sys.rest.model.dto.OperateOnlineDocDTO;
import com.dstz.sys.rest.model.dto.UpdateFileDTO;
import com.dstz.sys.rest.model.vo.CreateAndOpenVO;
import com.dstz.sys.rest.model.vo.OnlineDocApiVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统附件 前端控制器
 * </p>
 *
 * @author jinxia.hou
 * @since 2022-02-17
 */
@RestController
@RequestMapping(AbAppRestConstant.SYS_SERVICE_PREFIX + "/sysFile")
public class SysFileController extends AbCrudController<SysFile> {

    private static final Integer FILE_NAME_MAX_LENGTH = 60;
    //文件上传的默认字典分类
    private static final String DEFAULT_DIC_CODE = "mrfl";

    private final SysFileManager sysFileManager;

    public SysFileController(SysFileManager sysFileManager) {

        this.sysFileManager = sysFileManager;
    }

    /**
     * <pre>
     * </pre>
     *
     * @param file    文件本身
     * @param dicCode 文件字典分类编码
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ApiResponse<String> upload(@RequestParam("file") MultipartFile file, String dicCode) throws IOException {
        if (file.getOriginalFilename().length() > FILE_NAME_MAX_LENGTH) {
            throw new BusinessMessage(SysApiCodes.FILE_NAME_LENGTH_ERROR);
        }
        if (StrUtil.isEmpty(dicCode)) {
            dicCode = DEFAULT_DIC_CODE;
        }
        SysFile sysFile = sysFileManager.upload(file.getInputStream(), file.getOriginalFilename(), dicCode);
        return ApiResponse.success(sysFile.getId()).withMessage("上传成功");
    }

    /**
     * 浏览文件，支持图片
     *
     * @param fileId   文件ID
     * @param response 响应
     */
    @GetMapping(value = "/view/{fileId}")
    public void viewFile(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        SysFile sysFile = sysFileManager.getById(fileId);
        String mineType;
        if (Objects.isNull(sysFile) || StrUtil.isEmpty(mineType = FileUtil.getMimeType(sysFile.getName()))) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        InputStream inputStream = sysFileManager.download(fileId);
        if (inputStream == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        final Date nowDateTime = new Date();
        final Date expireDateTime = DateUtils.addYears(nowDateTime, 1);
        response.addHeader(HttpHeaders.EXPIRES, DatePattern.JDK_DATETIME_FORMAT.format(expireDateTime));
        response.addHeader(HttpHeaders.CACHE_CONTROL, String.format("max-age=%d", TimeUnit.MICROSECONDS.toSeconds(expireDateTime.getTime() - nowDateTime.getTime())));
        ServletUtil.write(response, inputStream, mineType);
    }

    /**
     * <pre>
     * </pre>
     *
     * @param fileId 文件名
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download")
    public void download(@RequestParam("fileId") String fileId, HttpServletResponse response) throws Exception {
        SysFile sysFile = sysFileManager.getById(fileId);
        if (Objects.isNull(sysFile)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        InputStream inputStream = sysFileManager.download(fileId);
        if (inputStream == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
//        String mimeType = StrUtil.nullToDefault(FileUtil.getMimeType(sysFile.getName()), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        ServletUtil.write(response, inputStream, MediaType.APPLICATION_OCTET_STREAM_VALUE, sysFile.getName());
    }

    @RequestMapping(value = "zip", method = RequestMethod.GET)
    public ResponseEntity<byte[]> zip(@RequestParam("fileIds") String fileIds) throws Exception {
        File zipDirectory = new File(StrUtil.join(FileUtils.getTempDirectoryPath(), File.separator, RandomUtil.randomString(6)));
        File zipFile = null;
        try {
            if (!zipDirectory.mkdir()) {
                throw new BusinessMessage(SysApiCodes.FILE_CREATE_DIR_ERROR.formatDefaultMessage(zipDirectory.getPath()));
            }
            downloadToDirectory(StrUtil.split(fileIds, CharUtil.COMMA), zipDirectory);
            zipFile = ZipUtil.zip(zipDirectory);
            final String zipName = DatePattern.PURE_DATETIME_FORMAT.format(new DateTime()) + ".zip";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(zipName, StandardCharsets.UTF_8.displayName()));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(Files.readAllBytes(zipFile.toPath()), headers, HttpStatus.OK);
        } finally {
            if (zipFile != null) {
                FileUtils.deleteQuietly(zipFile);
            }
            FileUtils.deleteDirectory(zipDirectory);
        }
    }

    /**
     * 下载到指定目录
     *
     * @param fileIds   文件编号集
     * @param directory 下载目录
     * @throws IOException 文件不存在或读取写入问题
     */
    private void downloadToDirectory(List<String> fileIds, File directory) throws IOException {
        for (String id : fileIds) {
            SysFile sysFile = sysFileManager.getById(id);
            if (sysFile == null) {
                throw new BusinessMessage(SysApiCodes.FILE_NOT_FOUND_ERROR.formatDefaultMessage(id));
            }
            try (InputStream downloadStream = sysFileManager.download(id)) {
                try (OutputStream fos = new FileOutputStream(new File(directory, sysFile.getName()))) {
                    IOUtils.copyLarge(downloadStream, fos);
                }
            }
        }
    }

    @RequestMapping(value = "del")
    public ApiResponse<String> del(@RequestParam("fileId") String fileId) throws Exception {
        sysFileManager.delete(fileId);
        return ApiResponse.success("删除成功");
    }

    @RequestMapping(value = "update")
    public ApiResponse update(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        String fileId = request.getParameter("fileId");
        String userId = request.getParameter("userId");
        Part file = request.getPart("file");

        UpdateFileDTO updateFileDTO = new UpdateFileDTO();
        updateFileDTO.setFileId(fileId);
        updateFileDTO.setFileStream(file.getInputStream());
        //todo 使用当前用户名
        updateFileDTO.setUserName("test");
        updateFileDTO.setUserId(userId);
        int result = sysFileManager.update(updateFileDTO);

        return ApiResponse.success(result).withMessage("更新成功"+result+"条");
    }

    @Override
    protected String getEntityDesc() {
        return "系统附件";
    }

    @RequestMapping(value = "open")
    public ApiResponse openFile(@RequestBody @Valid OperateOnlineDocDTO operateDTO){
        HttpServletRequest request = AbRequestUtils.getHttpServletRequest();
        String requestUrl =StrUtil.removeSuffix(request.getRequestURL(),request.getServletPath());

        return ApiResponse.success(sysFileManager.openFile(operateDTO,requestUrl));
    }

    @RequestMapping(value = "close")
    public ApiResponse closeFile(@RequestBody @Valid OperateOnlineDocDTO operateDTO){
        return ApiResponse.success(sysFileManager.closeFile(operateDTO));
    }


    /**
     * 创建并打开新文件
     * @param operateDTO
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "createAndOpen", method = RequestMethod.POST)
    public ApiResponse<CreateAndOpenVO> createAndOpen(@RequestBody OperateOnlineDocDTO operateDTO) {
        if (StrUtil.isEmpty(operateDTO.getFileName())){
            operateDTO.setFileName("新建文件.docx");
        }
        //新建文件
        SysFile sysFile = sysFileManager.upload( IoUtil.toUtf8Stream(""), operateDTO.getFileName(), DEFAULT_DIC_CODE);
        //打开文件
        if (sysFile !=null && StrUtil.isNotEmpty(sysFile.getId())){
            operateDTO.setFileId(sysFile.getId());
            HttpServletRequest request = AbRequestUtils.getHttpServletRequest();
            String requestUrl =StrUtil.removeSuffix(request.getRequestURL(),request.getServletPath());
            return ApiResponse.success(new CreateAndOpenVO(sysFileManager.openFile(operateDTO,requestUrl),sysFile.getId()));
        }
        return ApiResponse.fail(SysApiCodes.FILE_CREATE_FILE_ERROR.getCode(),SysApiCodes.FILE_CREATE_FILE_ERROR.getMessage());
    }
}
