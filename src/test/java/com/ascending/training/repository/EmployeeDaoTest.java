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
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDaoTest {
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;

    @Before
    public void init() {
        departmentDao = new DepartmentDaoImpl();
        employeeDao = new EmployeeDaoImpl();
    }

    @Test
    public void getEmployees() {
        List<Employee> employees = employeeDao.getEmployees();
        int expectedNumOfDept = 6;
        employees.forEach(em -> System.out.println(em.toString()));
        Assert.assertEquals(expectedNumOfDept, employees.size());
    }

    @Test
    public void getEmployeeById() {
        int id = 2;
        Employee employee = employeeDao.getEmployeeById(id);
        Assert.assertEquals(id, employee.getId());
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
        Employee employee = new Employee("wzheng", "Wenjia", "Zheng", "wenjia.zheng@ascending.com", "405 North Washington St. Falls Church, VA");
        Employee employee2 = employeeDao.getEmployeeByName(employee.getName());

        /*
        * The account will not be saved as cascade = CascadeType.REMOVE defined in Employee class
        * */
        Account account = new Account("checking", 99999.99f);
        Set<Account> accounts = new HashSet();
        accounts.add(account);
        employee.setAccounts(accounts);

        if (employee2 == null) {
            Department department = departmentDao.getDepartmentByName("HR");
            employee.setDepartment(department);
            employeeDao.save(employee);
            employee2 = employeeDao.getEmployeeByName(employee.getName());
        }

        employee.setId(employee2.getId());
        Assert.assertEquals(employee, employee2);
    }
}