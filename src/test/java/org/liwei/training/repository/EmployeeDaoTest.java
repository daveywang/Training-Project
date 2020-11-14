/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;


import org.liwei.training.init.AppInitializer;
import org.liwei.training.model.Account;
import org.liwei.training.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class EmployeeDaoTest {
    @Autowired
    private EmployeeDao employeeDao;
    private String deptName = "HR";

    @Before
    public void init() {
    }

    @Test
    public void getEmployees() {
        List<Employee> employees = employeeDao.getEmployees();
        int expectedNumOfDept = 5;
        employees.forEach(em -> System.out.println(em.toString()));
        Assert.assertEquals(expectedNumOfDept, employees.size());
    }

    @Test
    public void updateEmployeeAddress() {
        String name = "dwang";
        String address = "11126 Fairhaven Court, Fairfax, VA";
        employeeDao.updateEmployeeAddress(name, address);
        Employee employee = employeeDao.getEmployeeByName(name);
        Assert.assertEquals(address, employee.getAddress());
    }

    @Test
    public void save() {
        Employee employee = new Employee("wzheng", "Wenjia", "Zheng", "wenjia.zheng@liwei.org", "405 North Washington St. Falls Church, VA");
        Employee employee2 = employeeDao.getEmployeeByName(employee.getName());

        /*
        * The account will not be saved as cascade = CascadeType.REMOVE defined in Employee class
        * */
        Account account = new Account("checking", 99999.99f);
        Set<Account> accounts = new HashSet();
        accounts.add(account);
        employee.setAccounts(accounts);

        if (employee2 == null) {
            employeeDao.save(employee, deptName);
            employee2 = employeeDao.getEmployeeByName(employee.getName());
        }

        employee.setId(employee2.getId());
        Assert.assertEquals(employee, employee2);
    }
}