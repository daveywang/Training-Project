/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.exception;

import com.ascending.training.model.User;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class UserNotFoundException extends AppBaseException {
    private User user;

    public UserNotFoundException() {
        super(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "The User is not found, please check if the user's email or password is correct.");
    }

    public UserNotFoundException(String message) {
        this();
        if (message != null) this.message = message;
    }

    public UserNotFoundException(User user) {
        this();
        if (user != null) this.user = user;
    }

    public UserNotFoundException(String message, User user) {
        this();
        if (message != null) this.message = message;
        if (user != null) this.user = user;
    }

    @Override
    public Map<String, Object> getFormattedMessage() {
        Map<String, Object> map = super.getFormattedMessage();
        if (user != null) map.put("user", user);
        return map;
    }
}
