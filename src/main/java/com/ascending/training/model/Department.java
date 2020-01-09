/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "department")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department extends Model {
    public Department() {}
    public Department(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Employee> getEmployees() {
        /* This solve the session closed exception when the fetch type is lazy */
        try {
            int size = employees.size();
        }
        catch (Exception e) {
            return null;
        }

        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        /* Create link between parent and children objects automatically */
        for (Employee e : employees) {
            if (e.getDepartment() == null) e.setDepartment(this);
        }

        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location);
    }
}
