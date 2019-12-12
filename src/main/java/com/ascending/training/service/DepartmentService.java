/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.Department;
import com.ascending.training.repository.DepartmentDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DepartmentService {
    /* Filed injection */
    //@Autowired
    private Logger logger;
    //@Autowired
    //@Qualifier("departmentDaoImpl2")
    private DepartmentDao departmentDao;

    /* Constructor injection*/
    @Autowired
    public DepartmentService(Logger logger, DepartmentDao departmentDao) {
        this.logger = logger;
        this.departmentDao = departmentDao;
    }

    /* Setter injection */
//    @Autowired
//    public void setLogger(Logger logger) {
//        this.logger = logger;
//    }
//
//    @Autowired
//    public  void setDepartmentDao(DepartmentDao departmentDao) {
//        this.departmentDao = departmentDao;
//    }

    public boolean save(Department department) {
        return departmentDao.save(department);
    }

    public boolean update(Department department) {
        return departmentDao.update(department);
    }

    public boolean delete(String deptName) {
        return departmentDao.delete(deptName);
    }

    public List<Department> getDepartments() {
        return departmentDao.getDepartments();
    }
    public List<Department> getDepartmentsWithChildren() {
        return departmentDao.getDepartmentsWithChildren();
    }

    public Department getDepartmentByName(String deptName) {
        return departmentDao.getDepartmentByName(deptName);
    }

    public List<Object[]> getDepartmentAndEmployees(String deptName) {
        return departmentDao.getDepartmentAndEmployees(deptName);
    }

    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
        return departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
    }
}