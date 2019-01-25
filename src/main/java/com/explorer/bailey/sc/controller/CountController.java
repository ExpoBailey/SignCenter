package com.explorer.bailey.sc.controller;

import com.explorer.bailey.sc.model.SignModel;
import com.explorer.bailey.sc.service.ICountService;
import com.explorer.bailey.sc.utils.SessionUtils;
import com.minstone.mobile.core.spring.api.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/25
 */
@RestController
@RequestMapping("/api/count")
public class CountController {

    @Resource
    private ICountService countService;

    @GetMapping("/dates")
    public ApiResult findDateList(SignModel signModel) {
        return ApiResult.asserts(countService.findDateList(SessionUtils.getUser().getId(), signModel.getProjectIds()));
    }

}
