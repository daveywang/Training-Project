/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.model.Department;
import org.liwei.training.repository.DepartmentDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DepartmentServiceImpl implements DepartmentService {
    /* Filed injection */
    //@Autowired
    private Logger logger;
    //@Autowired
    //@Qualifier("departmentDaoImpl2")
    private DepartmentDao departmentDao;

    /* Constructor injection*/
    @Autowired
    public DepartmentServiceImpl(Logger logger, DepartmentDao departmentDao) {
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

    @Override
    public boolean save(Department department) {
        return departmentDao.save(department);
    }

    @Override
    public boolean update(Department department) {
        return departmentDao.update(department);
    }

    @Override
    public boolean delete(String deptName) {
        return departmentDao.delete(deptName);
    }

    @Override
    public List<Department> getDepartments() {
        return departmentDao.getDepartments();
    }

    @Override
    public List<Department> getDepartmentsWithChildren() {
        return departmentDao.getDepartmentsWithChildren();
    }

    @Override
    public Department getDepartmentByName(String deptName) {
        return departmentDao.getDepartmentByName(deptName);
    }

    @Override
    public List<Object[]> getDepartmentAndEmployees(String deptName) {
        return departmentDao.getDepartmentAndEmployees(deptName);
    }

    @Override
    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
        return departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
    }
}