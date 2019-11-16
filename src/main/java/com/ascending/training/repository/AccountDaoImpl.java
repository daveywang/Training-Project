/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Account;
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
public class AccountDaoImpl implements AccountDao {
    @Autowired private Logger logger;
    @Autowired private SessionFactory sessionFactory;
    @Autowired private EmployeeDao employeeDao;

    @Override
    public boolean save(Account account, String employeeName) {
        Transaction transaction = null;
        boolean isSuccess = false;

        try (Session session = sessionFactory.openSession()) {
            Employee employee = employeeDao.getEmployeeByName(employeeName);

            if (employee != null) {
                transaction = session.beginTransaction();
                account.setEmployee(employee);
                session.save(account);
                transaction.commit();
                isSuccess = true;
            }
            else {
                logger.debug(String.format("The employee [%s] doesn't exist.", employeeName));
            }
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The account %s was inserted into the table.", account.toString()));

        return isSuccess;
    }

    @Override
    public List<Account> getAccounts() {
        String hql = "FROM Account as act left join fetch act.employee";

        try (Session session = sessionFactory.openSession()) {
            Query<Account> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
            //return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    public Account getAccountById(int id) {
        String hql = "FROM Account as act join fetch act.employee where act.id = :id";

        try (Session session = sessionFactory.openSession()) {
            Query<Account> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }
}
