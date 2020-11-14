/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.model.Employee;
import org.liwei.training.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired private EmployeeDao employeeDao;

    public boolean save(Employee employee, String deptName) {
        return employeeDao.save(employee, deptName);
    }

    public int updateEmployeeAddress(String name, String address) {
        return employeeDao.updateEmployeeAddress(name, address);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    public Employee getEmployeeByName(String name) {
        return employeeDao.getEmployeeByName(name);
    }
}
