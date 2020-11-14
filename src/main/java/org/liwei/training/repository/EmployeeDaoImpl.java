/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Department;
import org.liwei.training.model.Employee;
import org.liwei.training.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired private Logger logger;
    @Autowired private DepartmentDao departmentDao;

    @Override
    public boolean save(Employee employee, String deptName) {
        Transaction transaction = null;
        boolean isSuccess = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Department department = departmentDao.getDepartmentByName(deptName);

            if (department != null) {
                transaction = session.beginTransaction();
                employee.setDepartment(department);
                session.save(employee);
                transaction.commit();
                isSuccess = true;
            }
            else {
                logger.debug(String.format("The department [%s] doesn't exist.", deptName));
            }
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The employee %s was inserted into the table.", employee.toString()));
        return isSuccess;
    }

    @Override
    public int updateEmployeeAddress(String name, String address) {
        String hql = "UPDATE Employee as em set em.address = :address where em.name = :name";
        int updatedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    @Override
    public List<Employee> getEmployees() {
        String hql = "FROM Employee em left join fetch em.accounts";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    public Employee getEmployeeByName(String name) {
        String hql = "FROM Employee as em left join fetch em.accounts where em.name = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);

            return query.uniqueResult();
        }
    }
}
