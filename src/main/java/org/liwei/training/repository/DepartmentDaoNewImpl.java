/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.interceptor.HibernateInterceptor;
import org.liwei.training.model.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DepartmentDaoNewImpl extends DepartmentDaoImpl implements DepartmentDaoNew {
    @Override
    public Department load(int id) {
        String msg;
        Transaction transaction = null;
        Department department = null;

        try (Session session = sessionFactory.withOptions().interceptor(new HibernateInterceptor()).openSession()) {
            transaction = session.beginTransaction();
            department = session.load(Department.class, id);
            transaction.commit();
            msg = String.format("The department %s was loaded.", department.toString());
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            msg = e.getMessage();
        }

        logger.debug(AppConstants.MSG_PREFIX + msg);
        return department;
    }
}
