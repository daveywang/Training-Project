/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.exception.AuthenticationException;
import com.ascending.training.exception.UserNotFoundException;
import com.ascending.training.model.User;
import com.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private Logger logger;
    private UserService userService;
    private static String AUTH_URI = "/auth";

    @Autowired
    public AuthServiceImpl(Logger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
    }

    @Override
    public int authorize(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
        String msg = "No valid token found.";
        if (uri.startsWith(AUTH_URI)) return HttpServletResponse.SC_ACCEPTED;

        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty()) return statusCode;

            Claims claims = JwtUtil.decodeJwtToken(token);
            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources");   break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }

            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

            msg = String.format("Verb: %s, allowed resources: %s", verb, allowedResources);
        }
        catch (Exception e) {
            if (e.getMessage() != null) msg = e.getMessage();
        }

        logger.debug(msg);
        return statusCode;
    }

    public Map<String, String> authenticate(User user) {
        String tokenKeyWord = "Authorization";
        String tokenType = "Bearer";

        logger.debug(user.toString());
        User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
        if (u == null) throw new UserNotFoundException(user);

        try {
            String token = JwtUtil.generateToken(u);
            Map<String, String> map = new HashMap();
            map.put(tokenKeyWord, tokenType + " " + token);
            return map;
        }
        catch (Exception e) {
            throw new AuthenticationException(e.getMessage(), user);
        }
    }

}
