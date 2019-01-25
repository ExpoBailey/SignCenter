package com.explorer.bailey.sc.service;

import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/25
 */
public interface ICountService {

    List<String> findDateList(Long userId, List<Long> projectIds);
}
