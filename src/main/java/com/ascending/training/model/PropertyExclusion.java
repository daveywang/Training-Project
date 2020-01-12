/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "property_exclusion")
public class PropertyExclusion extends Model {
    @Column(name = "class_name")
    private String className;

    @Column(name = "excluded_properties")
    private String excludedProperties;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExcludedProperties() {
        return excludedProperties;
    }

    public void setExcludedProperties(String excludedProperties) {
        this.excludedProperties = excludedProperties;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyExclusion that = (PropertyExclusion) o;
        return  id == that.id &&
                className.equals(that.className) &&
                role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, className, role);
    }
}
