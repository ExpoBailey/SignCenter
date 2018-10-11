package com.explorer.bailey.sc.common;

import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.service.ICoreService;
import com.explorer.bailey.sc.utils.CookieUtils;
import com.explorer.bailey.sc.utils.SessionUtils;
import com.minstone.common.utils.code.MD5CodeUtil;
import com.minstone.mobile.core.spring.api.ApiStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwj
 * @description 登录拦截器
 * @since 2018/5/8
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private ICoreService coreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = SessionUtils.getUser();
        boolean flag = false;
        if (user != null) {
            flag = true;
        } else {
            // 未登录的；判断其cookie是否有存用户名，存了用户名，帮其做登录
            Cookie cookie = CookieUtils.getCookie(WebConstant.COOKIE_NAME);
            if (cookie != null) {
                // 判断cookie是否被篡改过
                String userCode = CookieUtils.getCookieValue(WebConstant.COOKIE_NAME);
                String key = userCode + MD5CodeUtil.md5(userCode + CookieUtils.SAFE_KEY);
                if (key.equals(cookie.getValue())) {
                    // 未被改过，用户名是正确的
                    try {
                        flag = coreService.login(userCode, null);
                    } catch (ServiceException e) {
                        throw new ServiceException(ApiStatus.UNAUTHORIZED, e.getMessage());
                    }
                } else {
                    CookieUtils.removeCookie(WebConstant.COOKIE_NAME);
                    throw new ServiceException(ApiStatus.UNAUTHORIZED, "Cookie被篡改！");
                }
            }
        }
        if (!flag) {
            throw new ServiceException(ApiStatus.UNAUTHORIZED, "请先登录用户！");
        } else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
