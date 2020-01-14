/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.util;

import com.ascending.training.constant.AppConstants;
import com.ascending.training.model.PropertyExclusion;
import com.ascending.training.model.Role;
import com.ascending.training.model.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AppTools {
    private static Logger logger;

    @Autowired
    public AppTools(Logger logger) {
        this.logger = logger;
    }

    public static boolean isIgnoredWord(String word, List<String> excludedWords) {
        for (String excludedWord : excludedWords) {
            if (word.toLowerCase().contains(excludedWord)) return true;
        }

        return false;
    }

    public static String logInfo(HttpServletRequest req) {
        final List<String> excludedWords = Arrays.asList("newPasswd", "confirmPasswd", "passwd", "password");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

        String formData = null;
        String httpMethod = req.getMethod();

        Date startDateTime = new Date();
        String requestURL = req.getRequestURI();
        String userIP = req.getRemoteHost();
        String sessionID = req.getSession().getId();
        Enumeration<String> parameterNames = req.getParameterNames();
        List<String> parameters = new ArrayList();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (isIgnoredWord(paramName, excludedWords)) continue;

            String paramValues = Arrays.asList(req.getParameterValues(paramName)).toString();
            parameters.add(paramName + "=" + paramValues);
        }

        if (!parameters.isEmpty()) {
            formData = parameters.toString().replaceAll("^.|.$", "");
        }

        return  new StringBuilder("| ")
                .append(formatter.format(startDateTime)).append(" | ")
                .append(userIP).append(" | ")
                .append(httpMethod).append(" | ")
                .append(requestURL).append(" | ")
                .append(sessionID).append(" | ")
                .append("responseTime ms").append(" | ")
                .append(formData).toString();
    }

    public static boolean applyPropertyFilter(String className, Object[] state, String[] propertyNames) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        boolean isStateModified = false;

        if (request != null) {
            User user = (User)request.getAttribute(AppConstants.REQUEST_USER);
            if (user != null) {
                List<String> properties = Arrays.stream(propertyNames).map(s -> s.toLowerCase()).collect(Collectors.toList());
                List<Set<String>> exclusionList = new ArrayList();

                for (Role role : user.getRoles()) {
                    /* Get exclusions for the object of the class being processed */
                    Set<PropertyExclusion> classExclusions = role.getExclusions().stream().filter(e -> e.getClassName().equalsIgnoreCase(className)).collect(Collectors.toSet());
                    logger.debug(String.format(AppConstants.MSG_PREFIX + "User: %s, Role: %s, Class: %s, Exclusions: %s", user.getName(), role.getName(), className, classExclusions));

                    /* Put all role's exclusions of the object of the class being processed in the exclusionList */
                    if (classExclusions.isEmpty()) exclusionList.add(new HashSet());
                    for (PropertyExclusion exclusion : classExclusions) {
                        String[] exclusions = exclusion.getExcludedProperties().replaceAll("\\s", "").toLowerCase().split(",");
                        exclusionList.add(new HashSet(Arrays.asList(exclusions)));
                    }
                }

                //logger.debug(String.format(AppConstants.MSG_PREFIX + "Properties: %s", properties));
                logger.debug(String.format(AppConstants.MSG_PREFIX + "Exclusions: %s", exclusionList));

                /*
                   Intersect all exclusions in the exclusionList, the result is in commonExclusions.
                */
                Set<String> commonExclusions = exclusionList.get(0);
                for (int i = 1; i < exclusionList.size(); i++) {
                    commonExclusions.retainAll(exclusionList.get(i));
                }

                logger.debug(String.format(AppConstants.MSG_PREFIX + "Common Exclusions: %s", commonExclusions));

                /*
                   Set the value of property to be default value based on the commonExclusions,
                   so, the data of the excluded properties can be hidden to the users.
                */
                for (String exclusion : commonExclusions) {
                    int index = properties.indexOf(exclusion);

                    if (index != -1) {
                        if (state[index] instanceof Number) state[index] = 0;
                        else if (state[index] instanceof Character) state[index] = '\u0000';
                        else if (state[index] instanceof Boolean) state[index] = false;
                        else if (state[index] instanceof Byte) state[index] = 0;
                        else state[index] = null;
                        isStateModified = true;
                    }
               }
            }
        }

        return isStateModified;
    }

}
