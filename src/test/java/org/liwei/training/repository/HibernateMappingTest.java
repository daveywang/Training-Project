/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Department;
import org.liwei.training.util.HibernateUtil;
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
