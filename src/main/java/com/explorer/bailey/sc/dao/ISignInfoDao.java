package com.explorer.bailey.sc.dao;

import com.explorer.bailey.sc.entity.SignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
public interface ISignInfoDao extends JpaRepository<SignInfo, Long> {
}
