/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DepartmentDaoTest.class,
        EmployeeDaoTest.class,
        AccountDaoTest.class
})

public class TestAll {
}

/* command line run specific test suite by maven
mvn test -Dtest=com.ascending.training.repository.TestAll \
    -Ddatabase.driver=org.postgresql.Driver \
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect \
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db \
    -Ddatabase.user=admin \
    -Ddatabase.password=Training123!
    -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
 */