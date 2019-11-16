/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.interceptor.HibernateInterceptor;
import com.ascending.training.model.Account;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DepartmentDaoImpl2 implements DepartmentDao {
    @Autowired private Logger logger;
    @Autowired private SessionFactory sessionFactory;

    @Override
    public boolean save(Department department) {
        Transaction transaction = null;
        boolean isSuccess = true;
        Session session = null;

        try {
            session = sessionFactory.withOptions().interceptor(new HibernateInterceptor()).openSession();
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }

        if (isSuccess) logger.debug(String.format("The department %s was inserted into the table.", department.toString()));

        return isSuccess;
    }

    @Override
    public boolean update(Department department) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(department);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The department %s was updated.", department.toString()));

        return isSuccess;
    }

    @Override
    public boolean delete(String deptName) {
        String hql = "DELETE Department where name = :deptName1";
        int deletedCount = 0;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //Query<Department> query = session.createQuery(hql);
            //query.setParameter("deptName1", deptName);
            //deletedCount = query.executeUpdate();
            Department dept = getDepartmentByName(deptName);
            session.delete(dept);
            transaction.commit();
            deletedCount = 1;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The department %s was deleted", deptName));

        return deletedCount >= 1 ? true : false;
    }

    @Override
    public List<Department> getDepartments() {
        //String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        //String hql = "FROM Department as dept left join fetch dept.employees";
        String hql = "FROM Department";
        try (Session session = sessionFactory.openSession()) {
            Query<Department> query = session.createQuery(hql);
            //return query.list();
            return query.list().stream().distinct().collect(Collectors.toList());
            //return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    public List<Department> getDepartmentsWithChildren() {
        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        //String hql = "FROM Department as dept left join fetch dept.employees";
        try (Session session = sessionFactory.openSession()) {
            Query<Department> query = session.createQuery(hql);
            //return query.list();
            return query.list().stream().distinct().collect(Collectors.toList());
            //return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    public Department getDepartmentByName(String deptName) {
        if (deptName == null) return null;
        String hql = "FROM Department as dept left join fetch dept.employees as em left join " +
                     "fetch em.accounts where lower(dept.name) = :name";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";

        try (Session session = sessionFactory.openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public List<Object[]> getDepartmentAndEmployees(String deptName) {
        if (deptName == null) return null;

        String hql = "FROM Department as dept left join dept.employees where lower(dept.name) = :name";

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            List<Object[]> resultList = query.list();

            for (Object[] obj : resultList) {
                logger.debug(((Department)obj[0]).toString());
                logger.debug(((Employee)obj[1]).toString());
            }

            return resultList;
        }
    }

    @Override
    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
        if (deptName == null) return null;

        String hql = "FROM Department as dept " +
                     "left join dept.employees as ems " +
                     "left join ems.accounts as acnts " +
                     "where lower(dept.name) = :name";

        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            List<Object[]> resultList = query.list();

            for (Object[] obj : resultList) {
                logger.debug(((Department)obj[0]).toString());
                logger.debug(((Employee)obj[1]).toString());
                logger.debug(((Account)obj[2]).toString());
            }

            return resultList;
        }
    }
}
