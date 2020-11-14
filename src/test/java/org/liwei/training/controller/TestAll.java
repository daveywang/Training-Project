/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DepartmentControllerTest.class
})

public class TestAll {
}

/* command line run specific test suite by maven
mvn test -Dtest=org.liwei.training.controller.TestAll \
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
    -Ddatabase.user=admin
    -Ddatabase.password=Training123!
    -Dlogging.level.org.springframework=INFO
    -Dlogging.level.org.liwei=TRACE
    -Dserver.port=8080
    -Dsecret.key=Training123!@#
*/