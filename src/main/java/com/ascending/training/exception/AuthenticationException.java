/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.exception;

import com.ascending.training.model.User;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticationException extends RuntimeException {
    private String message = "Bad request.";
    private int statusCode = HttpStatus.BAD_REQUEST.value();
    private User user;

    public AuthenticationException(String message) {
        if (message != null) this.message = message;
    }

    public AuthenticationException(String message, User user) {
        if (message != null) this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, Object> getFormattedMessage() {
        Map<String, Object> map = new LinkedHashMap();
        map.put("status code", statusCode);
        map.put("message", message);
        if (user != null) map.put("user", user);
        return map;
    }
}
