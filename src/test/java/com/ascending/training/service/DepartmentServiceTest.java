/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import com.ascending.training.repository.DepartmentDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    @Autowired
    private Logger logger;
    @Autowired
    private DepartmentDao departmentDao;
    //@Autowired
    private DepartmentService departmentService;

    @Before
    public void init() {
        /*
            Demonstrate Constructor Injection is best way for DI, it ensure state safety of the object,
            in case there is no DI used in high-level modules,
            for example, here create the object DepartmentService by using new
        */
        departmentService = new DepartmentServiceImpl(logger, departmentDao);
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        int expectedNumOfDept = 4;

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

    @Ignore
    @Test
    public void saveDepartment() {
        Department department = new Department();
        department.setName("AAAA");
        department.setDescription("AAAAAAAAA");
        Employee employee = new Employee();
        employee.setName("ZZZZZ");
        department.addEmployee(employee);
        boolean isSaved = departmentService.save(department);
        Assert.assertTrue(isSaved);
    }
}