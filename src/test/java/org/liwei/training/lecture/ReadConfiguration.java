/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.lecture;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ReadConfiguration {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private String configFile = "application.properties";
    private String configXMLFile = "application.xml";

    @Test
    public void getConfFromFile() {
        Properties configuration = new Properties();
        Properties configurationXML = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
             InputStream inputStreamXML = this.getClass().getClassLoader().getResourceAsStream(configXMLFile)
        ) {
            configuration.load(inputStream);
            configurationXML.loadFromXML(inputStreamXML);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info(">>>>>>>>>> Configuration Properties File:" + configuration);
        logger.info(">>>>>>>>>> configuration XML File:" + configurationXML);
    }

    @Test
    public void getConfFromJVMOption() {
        Properties properties = System.getProperties();
        String databaseDriver = System.getProperty("database.driver");
        logger.info(">>>>>>>>>> Properties:" + properties);
        logger.info(">>>>>>>>>> database.driver:" + databaseDriver);
    }

    /*
        Set, show and remove Environment variable:
        1. linux - export VAR_NAME="this is value", echo $VAR_NAME, unset VAR_NAME
        2. Windows - set VAR_NAME="this is value", echo %VAR_NAME%,
     */
    @Test
    public void getConfFromEnv() {
        Map<String, String> env = System.getenv();
        String home = System.getenv("HOME");
        logger.info(">>>>>>>>>> Env: " + env);
        logger.info(">>>>>>>>>> Home: " + home);
    }
}
