/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved. 
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.init.AppInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class FakeTest {
    @Autowired private Logger logger;
    @Mock private Fake fake;
    //@Spy private Fake fake;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        /* All methods of mocked objects return default values of the return type, if there is no stubbing for the method */
        /* Stubbing */
        when(fake.getString()).thenReturn("NewString");
        when(fake.getInt()).thenReturn(1000);
        when(fake.getChar()).thenReturn('Z');
        when(fake.getDouble()).thenReturn(1000.00);
        when(fake.getNumber()).thenReturn(9999.99);
        when(fake.getBoolean()).thenReturn(true);
        when(fake.getObject()).thenReturn("Object");
    }

    @Test
    public void getIntTest() {
        logger.info(AppConstants.MSG_PREFIX + "Int: " + fake.getInt());
    }

    @Test
    public void getDoubleTest() {
        logger.info(AppConstants.MSG_PREFIX + "Double: " + fake.getDouble());
    }

    @Test
    public void getBooleanTest() {
        logger.info(AppConstants.MSG_PREFIX + "Boolean: " + fake.getBoolean());
    }

    @Test
    public void getCharTest() {
        logger.info(AppConstants.MSG_PREFIX + "Char: " + fake.getChar());
    }

    @Test
    public void getNumberTest() {
        logger.info(AppConstants.MSG_PREFIX + "Number: " + fake.getNumber());
    }

    @Test
    public void getStringTest() {
        logger.info(AppConstants.MSG_PREFIX + "String: " + fake.getString());
    }

    @Test
    public void getObjectTest() {
        logger.info(AppConstants.MSG_PREFIX + "Object: " + fake.getObject());
    }
}
