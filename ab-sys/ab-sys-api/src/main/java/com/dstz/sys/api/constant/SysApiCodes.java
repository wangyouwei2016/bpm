package com.dstz.sys.api.constant;

import com.dstz.base.common.codes.IBaseCode;

/**
 * @author jinxia.hou
 * @Name SysApiCodes
 * @description: 系统设置状态码
 * @date 2022/2/1611:42
 */
public enum SysApiCodes implements IBaseCode {

    KEY_WORD_DUPLICATE("CodeDuplicate", "{}系统中已存在!"),
    CODE_DUPLICATE("CodeDuplicate", "编码已存在!"),
    NAME_DUPLICATE("NameDuplicate", "名称已存在!"),


    //系统属性相关
    NOT_FOND_ENV_PROPERTY("NotFoundEnvProperty", "查找不到正确的环境属性配置"),

    //数据字典相关
    DICT_KEY_TYPE_ERROR("DictKeyTypeError", "字典类型只能是dict/node"),
    DICT_DELETE_ERROR("DictDeleteError", "系统内置【{}】不能删除"),

    //节假日
    WORK_CALENDAR_INITIAL_ERROR("WorkCalendarInitialError", "当前年份已经初始化过"),
    WORK_CALENDAR_START_END_TIME_ERROR("WorkCalendarStartEndTimeError", "开始日期大于结束日期"),
    WORK_CALENDAR_TIME_ERROR("WorkCalendarTimeError", "该时间段有日期已设定过不同节假日类型，请删除后添加或直接更新"),
    WORK_CALENDAR_SYSTEM_ERROR("WorkCalendarSystemError", "系统与类型不一致"),
    WORK_CALENDAR_CANT_NOT_CREATE("WorkCalendarCantNotCreate", "不支持创建"),
    WORK_CALENDAR_CANT_NOT_UPDATE("WorkCalendarCantNotUpdate", "不支持更新"),
    WORK_CALENDAR_YEAR_NOT_INITIAL("WorkCalendarYearNotInitial", "年份未初始化"),
    HOLIDAY_CONF_NAME_DUPLICATE("HolidayConfNameDuplicate", "重复添加,相同日期内只能有一个同名节假日"),
    HOLIDAY_CONF_NOT_FOUND("HolidayConfNotFound", "节假日配置不存在"),
    WORKCALENDAR_NOT_INIT("sys-workCalendar-not-init","工作日计算不可用，请配置 {} 年节假日!"),

    //日程相关
    SCHEDULE_CONF_TIME_ERROR("ScheduleConfTimeError", "完成时间不能小于实际开始日期"),

    //工作交接相关
    WORK_HANDOVER_USER_ERROR("WorkHandoverUserError", "用户({})工作已交接，请勿指派为接收!"),

    //数据源相关
    DATA_SOURCE_CLASSPATH_ERROR("DataSourceClasspathError", "classPath[{}]不是javax.sql.DataSource的子类"),
    DATA_SOURCE_CLASSPATH_PARAM_ERROR("DataSourceClasspathParamError", "根据classPath[{}]获取参数异常"),
    DATA_SOURCE_NOT_FOUND_ERROR("DataSourceNotFoundError", "数据源（{}）不可用，请联系管理员"),
    DATA_SOURCE_ATTRIBUTE_IS_EMPTY("DataSourceAttributeIsempty","数据源({})属性列表为空"),
    DATASOURCE_CONNECTION_EXCEPTION("DatasourceConnectionException", "数据源连接异常:{}"),

    //文件操作相关
    FILE_NOT_FOUND_ERROR("FileNotFoundError", "附件【{}】不存在"),
    FILE_NAME_LENGTH_ERROR("FileNameLengthError", "文件名字长度不能大于60字符"),
    FILE_CREATE_DIR_ERROR("CanNotCreateDirectory","创建目录失败{}"),
    FILE_OPEN_FILE_ERROR("OpenFileError","打开在线文档失败，【{}】"),
    FILE_CREATE_FILE_ERROR("createFileError","创建文档失败！"),

    //审计日志相关
    OPERATE_LOG_REMOVE_ERROR("OperateLogRemoveError", "尚有操作日志[{}]条关联，请先删除操作日志再操作"),

    //流水号相关
    SERIAL_NO_CODE_NOT_FOUND_ERROR("SerialNoCodeNotFound", "找不到流水号的相关信息！"),
    SERIAL_NO_GET_PARAM_ERROR("SerialNoGetParamError", "获取流水号参数错误,需要{}参数"),
    SERIAL_NO_REVIVE_SAVE_ERROR("SerialNoReviveLogSaveError", "保存流水号规则记录失败，请联系管理员"),
    SERIAL_NO_BUILD_ERROR("SerialNoBuildError", "生成流水号服务繁忙，请稍后再试！"),
    SERIAL_NO_EXECUTE_SCRIPT_ERROR("SerialNoeExecuteScriptError", "流水号参数脚本执行错误请检查！{}"),

    //CMS相关
    NOTIFY_HAS_USED("NotifyHasUsed", "公告列表正在使用该类型，请先删除关联的公告列表！"),
    REFLEX_WARNING("reflexWarning", "评论模块后台反射异常！"),
    JSON_CONVERSION_ERROR("jsonConversionError", "翻译列表中,名为【{}】的数据存在异常,请重新修改此数据"),
    TEMPLATE_CONVERSION_ERROR("templateConversionError", "模版数据转换出错,请检查消息模版内容!"),
    INIT_DATA_CANT_DELETE("initDataCantDelete", "常用语[{}]为内置数据,无法删除!"),
    KEY_REPEAT("keyRepeat", "别名[{}]与已有别名[{}]路径冲突"),

    CONNECT_RECORD_ERROR("ConnectRecordError", "信息:{}"),
    ;
    /**
     * 数据源连接异常
     */


    private final String code;

    private final String message;

    SysApiCodes(String code, String message) {
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
