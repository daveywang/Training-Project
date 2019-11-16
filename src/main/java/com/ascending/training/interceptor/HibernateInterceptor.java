/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.interceptor;

import com.ascending.training.model.Account;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
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
        return processData("onLoad", entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return processData("onSave", entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        processData("onDelete", entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        return processData("onLoad", entity, id, currentState, propertyNames, types);
    }

    private boolean processData(String method, Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(String.format(">>>>>>>>>> [onLoad - %s] - entity: %s", entity.getClass().getName(), entity.toString()));
        logger.info(String.format(">>>>>>>>>> [onLoad - %s] - id: %s", entity.getClass().getName(), id));
        logger.info(String.format(">>>>>>>>>> [onLoad - %s] - propertyNames: %s", entity.getClass().getName(), Arrays.deepToString(propertyNames)));
        logger.info(String.format(">>>>>>>>>> [onLoad - %s] - types: %s", entity.getClass().getName(), Arrays.deepToString(types)));

        if (entity instanceof Department) {
            state[0] = "Modified Data";
            return true;
        }
        if (entity instanceof Employee) {
            state[3] = (String)state[3] + " - EMAIL";
            return true;
        }
        else if (entity instanceof Account) {
            state[1] = (double)state[1] / 10.0;
            return true;
        }

        return false;
    }
}
