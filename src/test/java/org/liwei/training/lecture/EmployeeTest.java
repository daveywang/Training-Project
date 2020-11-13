/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved. 
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.lecture;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeTest {
    public static void main(String[] args) {
        Employee em1 = new Employee("dwang", "David", "Wang", "david.wang@org.org");
        Employee em2 = new Employee("rhang", "Ryo", "Hang", "ryo.hang@org.org");
        Employee em3 = new Employee("yle", "Yi", "Le", "yi.le@org.org");
        Employee em4 = new Employee("xhuang", "Xinyue", "Huang", "xinyue.huang@org.org");
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

        String s = ",10,11, 12, 13";
        s = Arrays.stream(s.split(",")).filter(e -> e.trim().length() > 0).map(e -> "'" + e.trim() + "'").collect(Collectors.joining(","));
        s = "11100";
        s = s.replaceFirst("^0+", "");
        System.out.println(s);


    }

}
