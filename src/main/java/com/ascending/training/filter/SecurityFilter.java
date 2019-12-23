/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.filter;

import com.ascending.training.service.AuthService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    @Autowired private Logger logger;
    @Autowired private AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.debug(">>>>>>>>>> Entering SecurityFilter...");
        HttpServletRequest req = (HttpServletRequest)request;

        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD");

        if (req.getMethod().equals(RequestMethod.OPTIONS.toString())) {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        int statusCode = authService.authorize(req);
        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode, "No valid token found.");
        logger.debug(">>>>>>>>>> Left SecurityFilter.");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug(">>>>>>>>>> Initializing SecurityFilter...");
    }

    @Override
    public void destroy() {
        logger.debug(">>>>>>>>>> SecurityFilter is destroyed!");
    }
}