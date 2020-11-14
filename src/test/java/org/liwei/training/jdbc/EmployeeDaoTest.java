/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.jdbc;

import org.liwei.training.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeDaoTest {
    private EmployeeDao employeeDao;
    private String employeeName = "dwang";

    @Before
    public void init() {
        employeeDao = new EmployeeDao();
    }

    @Test
    public void getEmployeeTest() {
        Employee employee = employeeDao.getEmployee(employeeName);
        Assert.assertEquals(employeeName, employee.getName());
    }
}
