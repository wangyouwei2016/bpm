package com.dstz.cms.api.constant;


/**
 * @author niu
 * @description CMS信息模块内用到的常量
 */
public class CmsConstant {

    
    /**
     * 发布状态:(未发布)
     */
    public static int STATUS_UNPUBLISHED = 0;

    /**
     * 发布状态:(已发布)
     */
    public static int STATUS_PUBLISHED = 1;
    
    /**
     * 发布状态:(下架)
     */
    public static int STATUS_DOWN = 2;
    
    /**
     * 已读状态 (未读)
     */
    public static int TYPE_UN_READ = 0;
    
    /**
     * 已读状态 (已读)
     */
    public static int TYPE_IS_READ = 1;
    
    /**
     * 已读状态 (过期)
     */
    public static int TYPE_EXPIRED = 2;
    
    /**
     * 查询条件 (标题模糊查询)
     */
    public static String TITLE$VLK = "title$VLK";
    
    /**
     * 查询条件 (状态精确查询)
     */
    public static String STATUS$VEQ = "status$VEQ";
    
    /**
     * 新闻或公告的评论数属性的字符串
     */
    public static String COMMENTS_NUM = "commentsNum";
    
    /**
     * 评论类型 (0公告, 1新闻)
     */
    public static int COMMENT_TYPE_NOTIFY = 0;
    
    /**
     * 评论类型 (0公告, 1新闻)
     */
    public static int COMMENT_TYPE_NEWS = 1;
    
    /**
     * 站内信的数据map的key-内容
     */
    public static String CONTENT = "content";
    
    /**
     * 站内信的数据map的key-标题
     */
    public static String SUBJECT = "subject";
    
    /**
     * 是否是所有人的key
     */
    public static String ALL = "all";
    
    /**
     * user的key
     */
    public static String USER = "user";
    
    /**
     * 编码的key
     */
    public static String CODE = "code";
    
    /**
     * 角色的key
     */
    public static String ROLE = "role";
    
    /**
     *    岗位的key
     */
    public static String POST = "post";
    
    /**
     * 组织的key
     */
    public static String ORG = "org";

    /**
     * 站内信推送类型(两种: 0通知, 1待办)
     */
    public static int INNER_NOTICE = 0;

    /**
     * 站内信推送类型(两种: 0通知, 1待办)
     */
    public static int INNER_TODO = 1;

    /**
     * 知识库我的文档类型(收藏, 我的, 借阅中, 借阅通过)
     */
    public static String DOCUMENT_FAVORITE  = "favorite";

    /**
     * 知识库我的文档类型(收藏, 我的, 借阅中, 借阅通过)
     */
    public static String DOCUMENT_MY  = "my";

    /**
     * 知识库我的文档类型(收藏, 我的, 借阅中, 借阅通过)
     */
    public static String DOCUMENT_BORROW  = "borrow";

    /**
     * 知识库我的文档类型(收藏, 我的, 借阅中, 借阅通过)
     */
    public static String DOCUMENT_PASS  = "pass";


    /**
     * 知识库我的文档类型(已拒绝)
     */
    public static String DOCUMENT_REJECT  = "reject";

    /**
     * 知识库我的文档类型(已归还)
     */
    public static String DOCUMENT_REVERT  = "revert";

    /**
     * 知识库我的文档类型(无权限)
     */
    public static String DOCUMENT_NONE  = "none";

    /**
     * 知识库我的文档来源 字符串
     */
    public static String DOCUMENT_SOURCE  = "source";
    /**
     * 知识库的根目录ID
     */
    public static String ROOT_ID  = "0";

    /**
     * 知识库类型 目录
     */
    public static String FIELD  = "field";
    /**
     * 知识库类型 文档
     */
    public static String DOC  = "doc";

    /**
     * 知识库的借阅为收藏的数据类型
     */
    public static Integer FAVORITE_TYPE  = 1;

    /**
     * 知识库 数据int类型bool:  有值/真
     */
    public static String INT_TRUE  =  "1";

    /**
     * 知识库 数据int类型bool:  空/假
     */
    public static String INT_FALSE =  "0";

    /**
     * 公告/新闻标题模板字符串
     */
    public static String TITLE =  "${title}";

    /**
     * 新闻模板code
     */
    public static String NEWS_MESSAGE_TEMPLATE =  "news_message_template";

    /**
     * 公告模板code
     */
    public static String NOTIFY_MESSAGE_TEMPLATE =  "notify_message_template";
}
