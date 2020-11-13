/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.init.AppInitializer;
import org.liwei.training.model.Department;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class DepartmentDaoTest {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private Logger logger;

    @Before
    public void init() {
        //departmentDao = new DepartmentDaoImpl();
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 4;

        departments.forEach(dept -> logger.info(AppConstants.MSG_PREFIX + dept));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentByName() {
        String deptName = "HR";
        Department department = departmentDao.getDepartmentByName(deptName);

        logger.info(AppConstants.MSG_PREFIX + department.toString());
        logger.info(AppConstants.MSG_PREFIX + department.getEmployees().toString());
        logger.info(AppConstants.MSG_PREFIX + department.getEmployees().stream().findFirst().get().getAccounts().toString());

        Assert.assertEquals(deptName, department.getName());
    }

    @Test
    public void updateDepartmentLocation() {
        String deptName = "R&D";
        String location = "Room 101, 999 Washington Ave. Falls Church, VA";
        Department department = departmentDao.getDepartmentByName(deptName);
        if (department != null) {
            department.setLocation(location);
            departmentDao.update(department);
            department = departmentDao.getDepartmentByName(deptName);
            Assert.assertEquals(location, department.getLocation());
        }
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
        Assert.assertEquals(2, resultList.size());
    }
}