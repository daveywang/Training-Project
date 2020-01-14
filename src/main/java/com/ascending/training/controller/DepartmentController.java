/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.controller;

import com.ascending.training.constant.AppConstants;
import com.ascending.training.model.Department;
import com.ascending.training.service.DepartmentService;
import com.ascending.training.service.DepartmentServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/departments", "/depts"})
public class DepartmentController {
    private Logger logger;
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(Logger logger, DepartmentServiceImpl departmentService) {
        this.logger = logger;
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public List<Department> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return departments;
    }

    @RequestMapping(value = "/with-children", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Department> getDepartmentsWithChildren() {
        List<Department> departments = departmentService.getDepartmentsWithChildren();
        return departments;
    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Department getDepartment(@PathVariable String deptName) {
        Department department = departmentService.getDepartmentByName(deptName);
        return department;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String creatDepartment(@RequestBody Department department) {
        logger.debug(AppConstants.MSG_PREFIX + "Department: " + department.toString());
        String msg = "The department was created.";
        boolean isSuccess = departmentService.save(department);

        if (!isSuccess) msg = "The department was not created.";

        return msg;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String updateDepartment(@RequestBody Department department) {
        logger.debug(AppConstants.MSG_PREFIX + "Department: " + department.toString());
        String msg = "The department was updated.";
        boolean isSuccess = departmentService.update(department);
        if (!isSuccess) msg = "The department was not updated.";

        return msg;
    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteDepartment(@PathVariable String deptName) {
        logger.debug(AppConstants.MSG_PREFIX + "Department name: " + deptName);
        String msg = "The department was deleted.";
        boolean isSuccess = departmentService.delete(deptName);
        if (!isSuccess) msg = "The department was not deleted.";

        return msg;
    }
}