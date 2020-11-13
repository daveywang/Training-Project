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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmployeeServiceImpl implements EmployeeService {
    //@Autowired
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public boolean save(Employee employee, String deptName) {
        return employeeDao.save(employee, deptName);
    }

    @Override
    public int updateEmployeeAddress(String name, String address) {
        return employeeDao.updateEmployeeAddress(name, address);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeDao.getEmployeeByName(name);
    }
}
