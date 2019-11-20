/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.jdbc;

import com.ascending.training.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //STEP 1: Database information
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/training_db";
    private static final String USER = "admin";
    private static final String PASS = "Training123!";

    public List<Department> getDepartments() {
        logger.info("Enter the method getDepartments...");
        List<Department> departments = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //STEP 2: Open a connection
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            logger.info("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM department";
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
            logger.error(e.getMessage());
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

//        logger.trace("Trace - Department size = " + departments.size());
//        logger.debug("Debug - Department size = " + departments.size());
//        logger.info("Info - Department size = " + departments.size());
//        logger.warn("Warn - Department size = " + departments.size());
//        logger.error("Error - Department size = " + departments.size());
        logger.info("Exit the method getDepartments");
        return departments;
    }

}

