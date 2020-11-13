/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {
    int authorize(HttpServletRequest req);
    Map<String, String> authenticate(User user);
}
