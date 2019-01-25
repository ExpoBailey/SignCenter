package com.explorer.bailey.sc.utils;

import com.minstone.mobile.core.spring.api.ApiPager;
import com.minstone.mobile.core.spring.api.model.PageResult;
import org.springframework.data.domain.Page;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/25
 */
public class PageUtils {

    public static <T> PageResult<T> pageToApiPager(Page<T> page) {
        ApiPager apiPager = new ApiPager(page.getNumber() + 1, page.getSize(), page.getTotalPages(), page.getTotalElements());
        return new PageResult<>(page.getContent(), apiPager);
    }

}
