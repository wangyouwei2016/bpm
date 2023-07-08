package com.dstz.component.uploader.constant;

import com.dstz.base.common.codes.IBaseCode;

/**
 * @author lightning
 * @since 2023-05-04
 */
public enum UploadCodes implements IBaseCode {
    //minio
    MINIO_ERROR("minio_error", "minio异常:{}"),
    MINIO_BUCKET_EXISTS_ERROR("minio_bucket_exists_error", "判断minio bucket是否存在接口错误:{}"),

    MINIO_CONF_MISS("minio_conf_miss", "minio缺少配置 ：{}"),
    MINIO_GET_FILE_ERROR("minio_get_file_error", "minio获取文件失败:{}"),
    MINIO_DEL_FILE_ERROR("minio_del_file_error", "minio移除文件失败:{}"),
    CREATE_BUCKET_ERROR("create_bucket_error", "创建minio bucket接口错误：{}"),
    MINIO_GET_ALL_BUCKET_FILE("minio_get_all_bucket_file", "minio 获取存储桶中的所有对象异常：{}"),
    MINIO_GET_OBJ_FILE_ERROR("minio_get_obj_file_error", "minio 获取对象的元数据错误:{}"),

    MINIO_GET_OBJ_URL_ERROR("minio_get_obj_url_error", "minio 文件访问路径错误:{}"),


    ;

    private final String code;

    private final String message;

    UploadCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
