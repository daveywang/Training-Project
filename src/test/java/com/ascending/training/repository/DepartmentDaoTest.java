/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class DepartmentDaoTest {
    private static DepartmentDao departmentDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
        departmentDao = new DepartmentDaoImpl();
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 8;

        //departments.forEach(dept -> System.out.println(dept));
        Department department = departments.get(0);
        Set<Employee> es = department.getEmployees();
        for (Employee  e : es) {
            Set<Account> as = e.getAccounts();
            for (Account a : as) {
                logger.info(a.getAccountType());
            }
        }
        //logger.info(String.valueOf(department.getEmployees()));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentByName() {
        String deptName = "HR";
        Department department = departmentDao.getDepartmentByName(deptName);

        logger.info(department.toString());
        logger.info(department.getEmployees().toString());
        logger.info(department.getEmployees().stream().findFirst().get().getAccounts().toString());

        Assert.assertEquals(deptName, department.getName());
    }

    @Test
    public void updateDepartmentLocation() {
        String deptName = "R&D";
        String location = "11126 Fairhaven Court, Fairfax, VA";
        Department department = departmentDao.getDepartmentByName(deptName);
        department.setLocation(location);
        departmentDao.update(department);
        department = departmentDao.getDepartmentByName(deptName);
        Assert.assertEquals(location, department.getLocation());
    }

    @Test
    public void getDepartmentAndEmployeesTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentDao.getDepartmentAndEmployees(deptName);
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void getDepartmentAndEmployeesAndAccountsTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
        Assert.assertEquals(4, resultList.size());
    }
}