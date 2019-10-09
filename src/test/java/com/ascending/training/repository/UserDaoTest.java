/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;


import com.ascending.training.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    private UserDaoImpl userDao;

    @Before
    public void init() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void getUsers() {
        List<User> users = userDao.getUsers();
        users.forEach(user -> System.out.println(user.toString()));
    }

    @Test
    public void save() {
        User user = new User("dwang", "David", "Wang", "david.wang@ascending.com");
        if (userDao.getUserByName("dwang") == null) userDao.save(user);
    }

    @After
    public void cleanup() {
        //Todo
    }
}