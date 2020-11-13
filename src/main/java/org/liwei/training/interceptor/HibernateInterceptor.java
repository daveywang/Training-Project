/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.interceptor;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.util.AppTools;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;

public class HibernateInterceptor extends EmptyInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        showInfo("onLoad", entity, id, state, propertyNames, types);
        return applyPropertyFilter("onLoad", entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        showInfo("onSave", entity, id, state, propertyNames, types);
        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        showInfo("onDelete", entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        showInfo("onFlushDirty", entity, id, currentState, propertyNames, types);
        return false;
    }

    private boolean applyPropertyFilter(String method, Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        /* Apply property filter on the object dynamically based on the user's role */
        return AppTools.applyPropertyFilter(entity.getClass().getSimpleName(), state, propertyNames);
    }

    private void showInfo(String method, Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //logger.debug(String.format(AppConstants.MSG_PREFIX + "[%s - %s] - entity: %s", method, entity.getClass().getSimpleName(), entity.toString()));
        logger.debug(String.format(AppConstants.MSG_PREFIX + "[%s - %s] - entity: %s, propertyNames: %s", method, entity.getClass().getSimpleName(), entity.toString(), Arrays.deepToString(propertyNames)));
        //logger.debug(String.format(AppConstants.MSG_PREFIX + "[%s - %s] - types: %s", method, entity.getClass().getSimpleName(), Arrays.deepToString(types)));
    }
}
