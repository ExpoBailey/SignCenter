package com.explorer.bailey.sc;

import com.explorer.bailey.sc.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author zhuwj
 * @description Web拦截器配置
 * @since 2018/2/24
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**/*").excludePathPatterns("/api/login/into");
    }

}
