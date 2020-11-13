/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.User;

public interface UserDao {
    boolean save(User user);
    User getUserById(int id);
    User getUserByEmail(String email);
    User getUserByCredentials(String email, String password);
}
