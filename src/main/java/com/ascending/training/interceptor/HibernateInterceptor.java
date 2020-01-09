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
        deleteData("onDelete", entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        return processData("onFlushDirty", entity, id, currentState, propertyNames, types);
    }

    private boolean processData(String method, Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(String.format(">>>>>>>>>> [%s - %s] - entity: %s", method, entity.getClass().getName(), entity.toString()));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - id: %s", method, entity.getClass().getName(), id));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - propertyNames: %s", method, entity.getClass().getName(), Arrays.deepToString(propertyNames)));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - types: %s", method, entity.getClass().getName(), Arrays.deepToString(types)));

        /* Demonstrate the data can be changed before the data is persisted */
        if (entity instanceof Department) {
            //Change the property description
            state[0] = "Changed by Hibernate interceptor.";
            return true;
        }
        if (entity instanceof Employee) {
            //Change the property email
            state[3] = (String)state[3] + " - Added by Hibernate interceptor.";
            return true;
        }
        else if (entity instanceof Account) {
            //Change the property balance
            state[1] = (double)state[1] / 10.0;
            return true;
        }

        return false;
    }

    private boolean deleteData(String method, Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info(String.format(">>>>>>>>>> [%s - %s] - entity: %s", method, entity.getClass().getName(), entity.toString()));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - id: %s", method, entity.getClass().getName(), id));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - propertyNames: %s", method, entity.getClass().getName(), Arrays.deepToString(propertyNames)));
        logger.info(String.format(">>>>>>>>>> [%s - %s] - types: %s", method, entity.getClass().getName(), Arrays.deepToString(types)));
        return false;
    }
}
