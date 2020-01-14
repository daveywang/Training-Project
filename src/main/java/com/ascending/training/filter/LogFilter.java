/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.filter;

import com.ascending.training.constant.AppConstants;
import com.ascending.training.util.AppTools;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "logFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class LogFilter implements Filter {
    @Autowired
    private Logger logger;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.debug(AppConstants.MSG_PREFIX + "Entering LogFilter...");
        long startTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest)request;
        String logInfo = AppTools.logInfo(req);
        filterChain.doFilter(request, response);
        logger.info(AppConstants.MSG_PREFIX + logInfo.replace("responseTime", String.valueOf(System.currentTimeMillis() - startTime)));
        logger.debug(AppConstants.MSG_PREFIX + "Left LogFilter.");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug(AppConstants.MSG_PREFIX + "Initializing LogFilter...");
    }

    @Override
    public void destroy() {
        logger.debug(AppConstants.MSG_PREFIX + "LogFilter is destroyed!");
    }
}