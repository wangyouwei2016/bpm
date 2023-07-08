package com.dstz.component.uploader.minio;


import cn.hutool.core.date.DateUtil;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.ApiException;
import com.dstz.base.common.exceptions.BusinessMessage;
import com.dstz.component.uploader.constant.UploadCodes;
import io.minio.*;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.dstz.component.uploader.constant.UploadCodes.*;

/**
 * <pre>
 * minio模板
 * </pre>
 *
 * @author lightning
 */
public class MinioTemplate {


    private MinioClient client;

    private String endpoint, accessKey, secretKey;

    public MinioTemplate(String endpoint, String accessKey, String secretKey) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        if (client == null) {
            try {
                client = getMinioClient();
            } catch (InvalidPortException e) {
                e.getStackTrace();
            } catch (InvalidEndpointException e) {
                e.getStackTrace();
            }
        }
    }

    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }


    /**
     * 判断 bucket是否存在
     *
     * @param bucketName 桶
     * @return
     */

    public boolean bucketExists(String bucketName) {

        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new ApiException(UploadCodes.MINIO_BUCKET_EXISTS_ERROR.formatDefaultMessage(e.getMessage()), e);
        }

    }


    /**
     * 创建 bucket
     *
     * @param bucketName 桶
     */

    public void makeBucket(String bucketName) {

        try {
            boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new ApiException(CREATE_BUCKET_ERROR.formatDefaultMessage(e.toString()), e);
        }

    }


    /**
     * 文件上传
     *
     * @param bucketName 桶
     * @param objectName 文件名
     * @param stream     文件流
     */

    public void putObject(String bucketName, String objectName, InputStream stream) {
        try {
            client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).build());
        } catch (Exception e) {
            throw new ApiException(MINIO_ERROR.formatDefaultMessage(e.toString(), e));
        }

    }


    /**
     * 删除文件
     *
     * @param bucketName 桶
     * @param objectName 文件名
     * @return
     */

    public void removeObject(String bucketName, String objectName) {

        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new ApiException(MINIO_DEL_FILE_ERROR.formatDefaultMessage(e.toString()), e);
        }

    }


    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 桶 存储桶名称
     * @return 桶中所有对象
     */
    public Iterable<Result<Item>> listObjects(String bucketName) {
        try {
            boolean flag = bucketExists(bucketName);
            if (flag) {
                return client.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new ApiException(MINIO_GET_ALL_BUCKET_FILE.formatDefaultMessage(e.toString()), e);
        }
        return null;

    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 桶 存储桶名称
     * @param objectName 文件名 存储桶里的对象名称
     * @return 文件流
     */

    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = null;
                try {
                    //stream = client.getObject(bucketName, objectName);
                    stream = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
                } catch (Exception e) {
                    throw new ApiException(MINIO_GET_FILE_ERROR.formatDefaultMessage(e.toString()), e);
                }
                return stream;
            }
        }
        return null;
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 桶 存储桶名称
     * @param objectName 文件名 存储桶里的对象名称
     * @return 元数据
     */
    public ObjectStat statObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = null;
            try {
                statObject = client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            } catch (Exception e) {
                throw new ApiException(MINIO_GET_OBJ_FILE_ERROR.formatDefaultMessage(e.toString()), e);
            }
            return statObject;
        }
        return null;
    }

    /**
     * 文件访问路径
     *
     * @param bucketName 桶 存储桶名称
     * @param objectName 文件名 存储桶里的对象名称
     * @return 文件url
     */
    public String getObjectUrl(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            try {
                url = client.getObjectUrl(bucketName, objectName);
            } catch (Exception e) {
                throw new ApiException(MINIO_GET_OBJ_URL_ERROR.formatDefaultMessage(e.toString()), e);
            }
        }
        return url;
    }

}