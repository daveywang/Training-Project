/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.model.User;
import org.liwei.training.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserServiceImpl implements UserService {
    //@Autowired
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        return userDao.getUserByCredentials(email, password);
    }
}
