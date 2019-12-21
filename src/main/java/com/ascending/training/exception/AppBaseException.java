/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AppBaseException extends RuntimeException {
    protected String message;
    protected int statusCode;

    public AppBaseException() {}

    public AppBaseException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
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
        return map;
    }
}
