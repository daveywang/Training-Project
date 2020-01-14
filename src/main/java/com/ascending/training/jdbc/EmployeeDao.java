/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.jdbc;

import com.ascending.training.model.Employee;
import java.sql.*;

public class EmployeeDao {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/training_db";
    private static final String USER = "admin";
    private static final String PASS = "Training123!";

    public Employee getEmployee(String employeeName) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Employee employee = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM employee WHERE name = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, employeeName);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String address = rs.getString("address");

                //Fill the object
                employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setEmail(email);
                employee.setAddress(address);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if(preparedStatement != null) preparedStatement.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return employee;
    }
}

