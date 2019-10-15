/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Employee;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void save(Employee employee) {
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The employee %s was inserted into the table.", employee.toString()));
    }

    public int updateEmployeeAddress(String name, String address) {
        String hql = "UPDATE Employee as em set em.address = :address where em.name = :name";
        int updatedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("address", address);

            transaction = session.beginTransaction();
            updatedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The employee %s was updated, total updated record(s): %d", name, updatedCount));

        return updatedCount;
    }

    public List<Employee> getEmployees() {
        String hql = "FROM Employee em left join fetch em.accounts";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            return query.list();
        }
    }

    public Employee getEmployeeByName(String name) {
        String hql = "FROM Employee as em left join fetch em.accounts where em.name = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);

            return query.uniqueResult();
        }
    }

    public Employee getEmployeeById(int id) {
        String hql = "FROM Employee as em left join fetch em.accounts where em.id = :id";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }
}