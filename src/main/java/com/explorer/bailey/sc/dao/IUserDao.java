package com.explorer.bailey.sc.dao;

import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.jpa.DefaultRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/8
 */
public interface IUserDao extends DefaultRepository<User, Long> {
    User findByUserCode(String userCode);
}
