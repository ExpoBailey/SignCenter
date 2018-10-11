package com.explorer.bailey.sc.common;

import com.minstone.mobile.core.spring.api.ApiException;
import com.minstone.mobile.core.spring.api.ApiResult;
import com.minstone.mobile.core.spring.api.ApiStatus;

/**
 * @author zhuwj
 * @description 业务异常类
 * @since 2018/5/3
 */
public class ServiceException extends ApiException {

    private static final long serialVersionUID = 8402012443305777535L;

    public ServiceException(ApiResult result, Throwable cause) {
        super(result, cause);
    }

    public ServiceException(ApiStatus status, String desc) {
        super(status, desc);
    }

}
