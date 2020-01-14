/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.listener;

import com.ascending.training.constant.AppConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Autowired
    private Logger logger;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.debug(AppConstants.MSG_PREFIX + "The Application is started.");
        String serverInfo = servletContextEvent.getServletContext().getServerInfo();
        logger.debug(AppConstants.MSG_PREFIX + "The server information: " + serverInfo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.debug(AppConstants.MSG_PREFIX + "The Application is destroyed!");
    }
}