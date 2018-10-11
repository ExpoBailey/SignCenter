package com.explorer.bailey.sc.utils;

import com.minstone.common.utils.code.MD5CodeUtil;
import com.minstone.mobile.core.common.utils.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;

/**
 * @author zhuwj
 * @description 操作Cookie工具类
 * @since 2018/5/2
 */
public class CookieUtils {

    public final static String SAFE_KEY = "SIGN_CENTER";

    public final static int MD5_LENGTH = 32;

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取Cookie
     * @param name
     * @return javax.servlet.http.Cookie
     * @author zhuwj
     */
    public static Cookie getCookie(String name) {
        Cookie[] cookies = getServletRequestAttributes().getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 添加cookie; cookie的值会在最后拼入一串以“cookieValue + SAFE_KEY”经过MD5加密的字串符。
     * @param cookieName 名称
     * @param cookieValue 值；
     * @param maxAge 有效时间（秒）
     * @param domain 跨域访问的后缀设置
     * @author zhuwj
     */
    public static void addCookie(String cookieName, String cookieValue, int maxAge, String domain) {
        Cookie cookie = getCookie(cookieName);
        String key = MD5CodeUtil.md5(cookieValue + SAFE_KEY);
        if (cookie == null) {
            cookie = new Cookie(cookieName, cookieValue + key);
        } else {
            // cookie的值进行更新
            cookie.setValue(cookieValue + key);
        }
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        if (domain != null) {
            cookie.setDomain(domain);
        }
        getServletRequestAttributes().getResponse().addCookie(cookie);
    }

    /**
     * 获取cookie的值；
     * @param cookieName 名
     * @return  String 该值剪去加密部分后返回。
     * @author zhuwj
     */
    public static String getCookieValue(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            String value = cookie.getValue();
            if (!StringUtils.isEmpty(value) && value.length() >= MD5_LENGTH) {
                value = value.substring(0, value.length() - MD5_LENGTH);
            }
            return value;
        }
        return null;
    }

    /**
     * 删除cookie
     * @param cookieName cookie名称
     * @author zhuwj
     */
    public static void removeCookie(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            getServletRequestAttributes().getResponse().addCookie(cookie);
        }
    }
}
