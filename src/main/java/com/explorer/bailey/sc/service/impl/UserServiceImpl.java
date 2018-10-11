package com.explorer.bailey.sc.service.impl;

import com.explorer.bailey.sc.dao.IUserDao;
import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.service.IUserService;
import com.minstone.common.utils.code.MD5CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/8
 */
@Service("userService")
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveUser(User user) {
        if (user.getId() == null) {
            user.setPassword(MD5CodeUtil.md5(user.getPassword()));
        }
        user = userDao.save(user);
        System.out.println("保存后：" + user);
        return user != null;
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public User getUserByUserCode(String userCode) {
        return userDao.findByUserCode(userCode);
    }

}
