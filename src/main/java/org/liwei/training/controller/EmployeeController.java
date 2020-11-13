/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.controller;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.model.Employee;
import org.liwei.training.service.EmployeeService;
import org.liwei.training.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/ems", "/employees"})
public class EmployeeController {
    private Logger logger;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(Logger logger, EmployeeServiceImpl employeeService) {
        this.logger = logger;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployee(@PathVariable String name) {
        return employeeService.getEmployeeByName(name);
    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String creatEmployee(@RequestBody Employee employee, @PathVariable String deptName) {
        logger.debug(String.format(AppConstants.MSG_PREFIX + "Department name: %s, employee: %s", deptName, employee.toString()));
        String msg = "The employee was created.";
        boolean isSuccess = employeeService.save(employee, deptName);

        if (!isSuccess) msg = "The employee was not created.";

        return msg;
    }
}