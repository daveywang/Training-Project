/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Department;
import java.util.List;

public interface DepartmentDao {
    boolean save(Department department);
    boolean update(Department department);
    boolean delete(String deptName);
    List<Department> getDepartments();
    List<Department> getDepartmentsWithChildren();
    Department getDepartmentByName(String deptName);
    List<Object[]> getDepartmentAndEmployees(String deptName);
    List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName);
}
