/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;


import org.liwei.training.model.Account;
import org.liwei.training.model.Department;
import org.liwei.training.model.Employee;
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
        int expectedNumOfDept = 7;
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
    public void getEmployeeByName() {
        String name = "dwang";
        Employee employee = employeeDao.getEmployeeByName(name);
        Assert.assertNotNull(employee);
    }

    @Test
    public void getEmployeeByDepartmentName() {
        String name = "HR";
        List<Employee> employees = employeeDao.getEmployeeByDepartmentName(name);
        Assert.assertNotNull(employees);
    }

    @Test
    public void updateEmployeeAddress() {
        String name = "dwang";
        String address = "999 Washington Ave, Fairfax, VA 22030";
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
            Department department = departmentDao.getDepartmentByName("HR");
            employee.setDepartment(department);
            employeeDao.save(employee);
            employee2 = employeeDao.getEmployeeByName(employee.getName());
        }

        employee.setId(employee2.getId());
        Assert.assertEquals(employee, employee2);
    }
}