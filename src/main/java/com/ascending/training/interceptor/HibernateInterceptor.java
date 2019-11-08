/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.interceptor;

import com.ascending.training.model.Department;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class HibernateInterceptor extends EmptyInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(">>>>>>>>>> [onLoad]: " + entity.toString());
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(">>>>>>>>>> [onSave]: " + entity.toString());
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(">>>>>>>>>> [onDelete]: " + entity.toString());
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof Department) {
            logger.info(">>>>>>>>>> [onFlushDirty]: " + entity.toString());
        }

        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
