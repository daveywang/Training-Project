/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeTest {
    public static void main(String[] args) {
        Employee em1 = new Employee("dwang", "David", "Wang", "david.wang@ascending.com");
        Employee em2 = new Employee("rhang", "Ryo", "Hang", "ryo.hang@ascending.com");
        Employee em3 = new Employee("yle", "Yi", "Le", "yi.le@ascending.com");
        Employee em4 = new Employee("xhuang", "Xinyue", "Huang", "xinyue.huang@ascending.com");
        List<Employee> employees = new ArrayList();

        employees.add(em4);
        employees.add(em2);
        employees.add(em3);
        employees.add(em1);

        employees.forEach(em -> System.out.println(em.toString()));

        Collections.sort(employees);
        System.out.println("\n************* sorted by name ************");
        employees.forEach(em -> System.out.println(em.toString()));


        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getLastName().compareTo(e2.getLastName());
            }
        });

        Comparator<Employee> c = (e1, e2) -> e1.getLastName().compareTo(e2.getLastName());
        Collections.sort(employees, c);
        System.out.println("\n************* sorted by last name ************");
        employees.forEach(em -> System.out.println(em.toString()));


    }

}
