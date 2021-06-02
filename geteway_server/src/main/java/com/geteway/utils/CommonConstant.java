package com.geteway.utils;

public interface CommonConstant {

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    public static final Integer DEL_FLAG_1 = 1;

    /**
     * 未删除
     */
    public static final Integer DEL_FLAG_0 = 0;

    /**
     * 系统日志类型： 登录
     */
    public static final int LOG_TYPE_1 = 1;

    /**
     * 系统日志类型： 操作
     */
    public static final int LOG_TYPE_2 = 2;

    /**
     * 操作日志类型： 查询
     */
    public static final int OPERATE_TYPE_1 = 1;

    /**
     * 操作日志类型： 添加
     */
    public static final int OPERATE_TYPE_2 = 2;

    /**
     * 操作日志类型： 更新
     */
    public static final int OPERATE_TYPE_3 = 3;

    /**
     * 操作日志类型： 删除
     */
    public static final int OPERATE_TYPE_4 = 4;

    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;

    /**
     * {@code 501 Server Error} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_TP_NO_AUTHZ = 501;
    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_OK_200 = 200;

    public static final Integer SC_OK_0 = 0;

    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";

    /**
     * 父ID
     */
    public static final String PARENT_ID = "0";

    /**
     * 字典翻译文本后缀
     */
    public static final String DICT_TEXT_SUFFIX = "_dictText";

    /**
     * 标签翻译文本后缀
     */
    public static final String TAG_TEXT_SUFFIX = "tagList";

    /**
     * 待受理
     */
    public static final Integer ACCEPT_STATUS_TO = 0;

    /**
     * 进行中
     */
    public static final Integer ACCEPT_STATUS_HAS = 1;

    /**
     * 已完成
     */
    public static final Integer ACCEPT_STATUS_END = 2;

    public static final String UPLOAD_PATH = "//opt//upFiles";
}
