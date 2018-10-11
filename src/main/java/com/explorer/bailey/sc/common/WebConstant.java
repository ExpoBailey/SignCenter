package com.explorer.bailey.sc.common;

/**
 * 常量类<br>
 * @description 系统中常用到的一些公共常量。如：系统标识
 * @author zhuwj
 * @since 2017年10月20日
 */
public class WebConstant {

    /**
     * Session中的用户标识
     */
    public final static String SESSION_USER = "SESSION_USER";

    /**
     * 记住用户的标识
     */
    public final static int REMEMBER_USER = 1;

    /**
     * Cookie名称
     */
    public final static String COOKIE_NAME = "SIGN_CENTER_WEB_COOKIE";

    /**
     * cookie最大时长
     */
    public final static int MAX_COOKIE = 60 * 60 * 24 * 365;

}
