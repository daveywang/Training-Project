/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;


import org.liwei.training.model.Role;
import org.liwei.training.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserDaoTest {
    @Autowired
    private Logger logger;
    private UserDao userDao;
    private RoleDao roleDao;
    private String email;
    private List<Role> roles = new ArrayList();

    @Before
    public void init() {
        userDao = new UserDaoImpl();
        roleDao = new RoleDaoImpl();
        email = "dwang@liwei.org";
        roles.add(roleDao.getRoleByName("Manager"));
        roles.add(roleDao.getRoleByName("User"));
    }

    @Test
    public void getUserByEmail() {
        User user = userDao.getUserByEmail(email);
        Assert.assertNotNull(user);
        logger.debug(user.toString());
    }

    @Ignore
    @Test
    public void createUser() {
        User user = new User();
        user.setRoles(roles);
        user.setName("jfang");
        user.setFirstName("John");
        user.setLastName("Fang");
        user.setEmail("jfang@liwei.org");
        user.setPassword("jfang123!@#$");
        boolean result = userDao.save(user);
        Assert.assertTrue(result);
    }

    @Test
    public void encryptPassword() {
        logger.debug("Hashed Password: " + DigestUtils.md5Hex("123456789"));
    }
}