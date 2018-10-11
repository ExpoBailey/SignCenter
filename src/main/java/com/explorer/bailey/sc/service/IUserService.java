package com.explorer.bailey.sc.service;

import com.explorer.bailey.sc.entity.User;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/8
 */
public interface IUserService {

    boolean saveUser(User user);

    User getUserById(Long id);

    User getUserByUserCode(String userCode);

}
