/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.Department;

import java.util.List;

public interface DepartmentService {
    boolean save(Department department);
    boolean update(Department department);
    boolean delete(String deptName);
    List<Department> getDepartments();
    List<Department> getDepartmentsWithChildren();
    Department getDepartmentByName(String deptName);
    List<Object[]> getDepartmentAndEmployees(String deptName);
    List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName);
}
