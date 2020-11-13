/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.jdbc;

import org.liwei.training.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/training_db";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM department";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()) {
                //Retrieve by column name
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");

                //Fill the object
                Department department = new Department();
                department.setId(id);
                department.setName(name);
                department.setDescription(description);
                department.setLocation(location);
                departments.add(department);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return departments;
    }

}

