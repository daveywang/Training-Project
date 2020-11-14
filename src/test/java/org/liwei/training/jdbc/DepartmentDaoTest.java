/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.jdbc;

import org.liwei.training.model.Department;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DepartmentDaoTest {
    private DepartmentDao departmentDao;

    @Before
    public void init() {
        departmentDao = new DepartmentDao();
    }

    @Test
    public void getDepartmentsTest() {
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 4;

        for (Department department : departments) {
            System.out.println(department);
        }

        Assert.assertEquals(expectedNumOfDept, departments.size());
    }
}
