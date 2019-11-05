/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Department;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void mappingTest() {
        String hql = "FROM Department";
        List<Department> departments = null;

        try (
            Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            departments = query.list();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        departments.forEach(dept -> logger.info(dept.toString()));

        Assert.assertNotNull(departments);
    }
}
