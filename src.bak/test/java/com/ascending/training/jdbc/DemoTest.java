/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

/*
    mvn -Dtest=DemoTest -Dorg.slf4j.simpleLogger.defaultLogLevel=info test
 */

package com.ascending.training.jdbc;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoTest {
    private static Logger logger = LoggerFactory.getLogger(DemoTest.class);

    @BeforeClass
    public static void initAllTest() {
        logger.info("********** BeforeClass: start testing... **********");
    }

    @AfterClass
    public static void endAllTest() {
        logger.info("********** AfterClass: the tests are done! **********");
    }

    @Before
    public void initTest() {
        logger.info(">>>>>>>>>> Before: start unit testing... <<<<<<<<<<");
    }

    @After
    public void endTest() {
        logger.info(">>>>>>>>>> After: unite test are done. <<<<<<<<<<<\n");
    }

    @Test
    public void test1() {
        logger.trace("########## Test1 - Trace: test1 is done. ##########");
        logger.debug("########## Test1 - Debug: test1 is done. ##########");
        logger.info("########## Test1 - Info: test1 is done. ##########");
        logger.warn("########## Test1 - Warn: test1 is done. ##########");
        logger.error("########## Test1 - Error: test1 is done. ##########");

        String str1 = new String("ABC");
        String str2 = new String("ABC");
        String str3 = "ABC";
        String str4 = "ABC";

        Assert.assertEquals(str3, str4);
        Assert.assertSame(str3, str4);
    }

    @Test
    public void test2() {
        logger.trace("########## Test2 - Trace: test2 is done. ##########");
        logger.debug("########## Test2 - Debug: test2 is done. ##########");
        logger.info("########## Test2 - Info: test2 is done. ##########");
        logger.warn("########## Test2 - Warn: test2 is done. ##########");
        logger.error("########## Test2 - Error: test2 is done. ##########");
    }
}
