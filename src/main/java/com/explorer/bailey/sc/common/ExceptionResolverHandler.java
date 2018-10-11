package com.explorer.bailey.sc.common;

import com.minstone.common.exception.CommonException;
import com.minstone.mobile.core.spring.api.ApiResult;
import com.minstone.mobile.core.spring.api.ApiStatus;
import com.minstone.mobile.core.spring.api.configuration.JsonExceptionResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwj
 * @description
 * @since 2018/5/3
 */
@Component
@Order(-1000)
public class ExceptionResolverHandler implements HandlerExceptionResolver {

    @Value("${debug:false}")
    private boolean debug;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApiStatus apiStatus = null;
        int status = 500;
        String desc = "";
        if (ex instanceof CommonException) {
            status = ((CommonException) ex).getCode();
            desc = ((CommonException) ex).getMsg();
        } else {
            // 使用核心库的异常处理类
            return new JsonExceptionResolver(debug).resolveException(request, response, handler, ex);
        }
        // 选状态码，返回异常数据
        apiStatus = selectApiStatus(status);
        return new ModelAndView(new MappingJackson2JsonView(), ApiResult.failure(apiStatus, desc).toMap());
    }

    /**
     * 选择ApiStatus
     * @param status
     * @return com.minstone.mobile.core.spring.api.ApiStatus
     * @author zhuwj
     */
    private ApiStatus selectApiStatus(int status) {
        ApiStatus apiStatus = null;
        if (status == ApiStatus.BAD_REQUEST.getValue()) {
            apiStatus = ApiStatus.BAD_REQUEST;
        } else if (status == ApiStatus.UNAUTHORIZED.getValue()) {
            apiStatus = ApiStatus.UNAUTHORIZED;
        } else if (status == ApiStatus.FORBIDDEN.getValue()) {
            apiStatus = ApiStatus.FORBIDDEN;
        } else if (status == ApiStatus.NOT_FOUND.getValue()) {
            apiStatus = ApiStatus.NOT_FOUND;
        } else {
            apiStatus = ApiStatus.SERVER_ERROR;
        }
        return apiStatus;
    }
}
