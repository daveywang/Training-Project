/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {
    int authorize(HttpServletRequest req);
    Map<String, String> authenticate(User user);
}
