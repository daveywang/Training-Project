/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.model.Department;
import org.liwei.training.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    //@Autowired
    private Logger logger;
    //@Autowired
    private SessionFactory sessionFactory;
    //@Autowired
    private DepartmentDao departmentDao;

    @Autowired
    public EmployeeDaoImpl(Logger logger, SessionFactory sessionFactory, DepartmentDao departmentDao) {
        this.logger = logger;
        this.sessionFactory = sessionFactory;
        this.departmentDao = departmentDao;
    }

    @Override
    public boolean save(Employee employee, String deptName) {
        String msg = String.format("The employee %s was inserted into the table.", employee.toString());
        Transaction transaction = null;
        boolean isSuccess = false;

        try (Session session = sessionFactory.openSession()) {
            Department department = departmentDao.getDepartmentByName(deptName);

            if (department != null) {
                transaction = session.beginTransaction();
                employee.setDepartment(department);
                session.save(employee);
                transaction.commit();
                isSuccess = true;
            }
            else {
                msg = String.format("The department [%s] doesn't exist.", deptName);
            }
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            msg = e.getMessage();
        }

        logger.debug(AppConstants.MSG_PREFIX + msg);
        return isSuccess;
    }

    @Override
    public int updateEmployeeAddress(String name, String address) {
        String msg;
        String hql = "UPDATE Employee as em set em.address = :address where em.name = :name";
        int updatedCount = 0;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("address", address);

            transaction = session.beginTransaction();
            updatedCount = query.executeUpdate();
            transaction.commit();
            msg = String.format("The employee %s was updated, total updated record(s): %d", name, updatedCount);
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            msg = e.getMessage();
        }

        logger.debug(AppConstants.MSG_PREFIX + msg);
        return updatedCount;
    }

    @Override
    @Cacheable(cacheNames = "employees")
    public List<Employee> getEmployees() {
        String hql = "FROM Employee em left join fetch em.accounts";

        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
            //return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    @Cacheable(cacheNames = "employees")
    public Employee getEmployeeByName(String name) {
        String hql = "FROM Employee as em left join fetch em.accounts where em.name = :name";

        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);
            List<Employee> employees = query.list();
            Employee employee = employees.size() > 0 ? employees.get(0) : null;
            return employee;
        }
    }
}
