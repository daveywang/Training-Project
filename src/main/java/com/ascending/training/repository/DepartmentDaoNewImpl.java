/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.interceptor.HibernateInterceptor;
import com.ascending.training.model.Department;
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

        logger.debug(msg);
        return department;
    }
}
