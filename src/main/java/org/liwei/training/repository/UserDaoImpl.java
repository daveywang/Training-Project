/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    //@Autowired
    private Logger logger;
    //@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(Logger logger, SessionFactory sessionFactory) {
        this.logger = logger;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(User user) {
        String msg = String.format("The user %s was inserted into the table.", user.toString());
        Transaction transaction = null;
        boolean isSuccess = true;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            msg = e.getMessage();
        }

        logger.debug(AppConstants.MSG_PREFIX + msg);
        return isSuccess;
    }

    @Override
    public User getUserById(int id) {
        String hql = "FROM User as u where id = :id";

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User as u where lower(u.email) = :email";

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        String hql = "FROM User as u where lower(u.email) = :email and u.password = :password";
        logger.debug(String.format("User email: %s, password: %s", email, password));

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);

            return query.uniqueResult();
        }
    }
}
