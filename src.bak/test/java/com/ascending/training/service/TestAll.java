/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DepartmentServiceTest.class,
        EmployeeServiceTest.class,
        AccountServiceTest.class
})

public class TestAll {
}

/* command line run specific test suite by maven
mvn test -Dtest=com.ascending.training.service.TestAll \
    -Ddatabase.driver=org.postgresql.Driver \
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect \
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db \
    -Ddatabase.user=admin \
    -Ddatabase.password=Training123! \
    -Dlogging.level.org.springframework=INFO \
    -Dspring.main.banner-mode=OFF \
    -Dlogging.level.com.ascending=TRACE \
    -Dserver.port=8080 \
    -Dsecret.key=Training123!@#
 */

/*
    Tests follow a typical four phase sequence:
    1. setup - Initialization for each test case
    2. exercise - This is where the object is prodded to do the thing that we want to test.
    3. verify - The assert statements are then the verification stage, checking to see if the exercised method carried out its task correctly.
    4. teardown - Cleanup the test for each test case
 */