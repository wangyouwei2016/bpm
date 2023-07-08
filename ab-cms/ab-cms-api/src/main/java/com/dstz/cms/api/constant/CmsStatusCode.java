package com.dstz.cms.api.constant;

import com.dstz.base.common.codes.IBaseCode;

/**
 * @author lzl
 * @retrofit niu
 * @description CMS模块错误码枚举类
 */
public enum CmsStatusCode implements IBaseCode {

    PARAM_INVALID("paramInvalid", "参数无效,未找到匹配的数据"),
    BUS_SRC_REL("busSrcRel", "要删除的资源被关联,无法进行删除"),
    BUS_SRC_STS_UPDATE("busSrcStsUpdate", "要操作的资源状态已改变,无法进行操作"),
    NOTIFY_READ_ONLY("notifyReadOnly", "当前公告已发布,已发布的公告不可修改"),
    NEWS_READ_ONLY("newsReadOnly", "当前新闻已发布,已发布的新闻不可修改"),
    NOTIFY_DELETE_DISABLED("notifyDeleteDisabled", "当前公告为发布状态,请先下架公告后才能删除!"),
    NEWS_DELETE_DISABLED("newsDeleteDisabled", "当前新闻为发布状态,请先下架公告后才能删除!"),
    DOCUMENT_DELETE_FAIL("documentDeleteFail", "删除失败,此目录下存在文档,请先修改文档归属目录!"),
    BORROW_FAIL("borrowFail", "借阅失败,借阅已申请或已通过!"),
    DOWNLOAD_FAIL("downloadFail", "下载失败,缺少必要参数!"),
    DOWNLOAD_ERROR("downloadError", "下载出错,请联系管理员!"),
    SAVE_ERROR("saveError", "保存失败,请稍后重试"),
    COLUMN_TOO_LONG("columnTooLong", "字段[{}]太长,超出数据库字段限制!"),
    PORTAL_DELETE_FAIL("portalDeleteFail", "【{}】为系统内置门户，禁止删除!");

    private final String code;
    private final String message;

    CmsStatusCode(String code, String message) {
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
