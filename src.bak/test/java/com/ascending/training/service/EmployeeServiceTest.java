/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;


import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Account;
import com.ascending.training.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class EmployeeServiceTest {
    @Autowired private Logger logger;
    @Autowired private EmployeeService employeeService;
    @Autowired private DepartmentService departmentService;
    private String deptName = "HR";

    @Before
    public void init() {
        //departmentService = new DepartmentServiceImpl();
        //employeeService = new EmployeeServiceImpl();
    }

    @Test
    public void getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        int expectedNumOfDept = 5;
        employees.forEach(em -> logger.info(em.toString()));
        Assert.assertEquals(expectedNumOfDept, employees.size());
    }

    @Test
    public void updateEmployeeAddress() {
        String name = "dwang";
        String address = "11126 Fairhaven Court, Fairfax, VA";
        employeeService.updateEmployeeAddress(name, address);
        Employee employee = employeeService.getEmployeeByName(name);
        Assert.assertEquals(address, employee.getAddress());
    }

    @Test
    public void save() {
        Employee employee = new Employee("wzheng", "Wenjia", "Zheng", "wenjia.zheng@ascending.com", "405 North Washington St. Falls Church, VA");
        Employee employee2 = employeeService.getEmployeeByName(employee.getName());

        /*
        * The account will not be saved as cascade = CascadeType.REMOVE defined in Employee class
        * */
        Account account = new Account("checking", 99999.99f);
        Set<Account> accounts = new HashSet();
        accounts.add(account);
        employee.setAccounts(accounts);

        if (employee2 == null) {
            employeeService.save(employee, deptName);
            employee2 = employeeService.getEmployeeByName(employee.getName());
        }

        employee.setId(employee2.getId());
        Assert.assertEquals(employee, employee2);
    }
}