/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Employee;

import java.util.List;

public interface EmployeeDao {
    boolean save(Employee employee, String deptName);
    int updateEmployeeAddress(String name, String address);
    List<Employee> getEmployees();
    Employee getEmployeeByName(String name);
}
