/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.Employee;

import java.util.List;

public interface EmployeeService {
    boolean save(Employee employee, String deptName);
    int updateEmployeeAddress(String name, String address);
    List<Employee> getEmployees();
    Employee getEmployeeByName(String name);
}
