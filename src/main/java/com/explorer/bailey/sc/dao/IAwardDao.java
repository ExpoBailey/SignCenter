package com.explorer.bailey.sc.dao;

import com.explorer.bailey.sc.entity.Award;
import com.explorer.bailey.sc.jpa.DefaultRepository;

import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
public interface IAwardDao extends DefaultRepository<Award, Long> {
    List<Award> findByStatus(int status);
}
