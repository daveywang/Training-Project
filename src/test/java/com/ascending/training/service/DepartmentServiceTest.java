/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
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
public class DepartmentServiceTest {
    @Autowired private Logger logger;
    @Autowired private DepartmentService departmentService;

    @Before
    public void init() {
        //departmentService = new DepartmentServiceImpl();
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        int expectedNumOfDept = 3;

        departments.forEach(dept -> logger.info(dept.toString()));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentByName() {
        String deptName = "R&D";
        Department department = departmentService.getDepartmentByName(deptName);

        logger.info(department.toString());
        logger.info(department.getEmployees().toString());
        logger.info(department.getEmployees().stream().findFirst().get().getAccounts().toString());

        Assert.assertEquals(deptName, department.getName());
    }

    @Test
    public void updateDepartmentLocation() {
        String deptName = "R&D";
        String location = "11126 Fairhaven Court, Fairfax, VA";
        Department department = departmentService.getDepartmentByName(deptName);
        department.setLocation(location);
        departmentService.update(department);
        department = departmentService.getDepartmentByName(deptName);
        Assert.assertEquals(location, department.getLocation());
    }

    @Test
    public void getDepartmentAndEmployeesTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentService.getDepartmentAndEmployees(deptName);
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void getDepartmentAndEmployeesAndAccountsTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentService.getDepartmentAndEmployeesAndAccounts(deptName);
        Assert.assertEquals(4, resultList.size());
    }

    @Test
    public void deleteDepartment() {
        //departmentService.delete("HR");
    }
}