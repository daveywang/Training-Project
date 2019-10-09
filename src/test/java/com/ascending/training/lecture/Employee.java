/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.lecture;

public class Employee implements Comparable<Employee> {
    private String name;
    private String firstName;
    private String lastName;
    private String email;

    public Employee(String name, String firstName, String lastName, String email) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public int compareTo(Employee employee) {
        return name.compareTo(employee.getName());
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10s %-20s", name, firstName, lastName, email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
