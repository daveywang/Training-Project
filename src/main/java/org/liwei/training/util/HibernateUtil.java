/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.util;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.interceptor.HibernateInterceptor;
import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Logger logger;
    private static String C3P0_MIN_SIZE;

    @Autowired
    public HibernateUtil(Logger logger) {
        this.logger = logger;
    }


    /* Define JVM options
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
    -Ddatabase.user=admin
    -Ddatabase.password=Training123!
    */

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperties(getSettings());
                configuration.setInterceptor(new HibernateInterceptor());

                String[] modelPackages = {"org.liwei.training.model"};
                EntityScanner.scanPackages(modelPackages).addTo(configuration);

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            catch (Exception e) {
                logger.error(AppConstants.MSG_PREFIX + e.getMessage());
            }
        }

        return sessionFactory;
    }

    private static Properties getSettings() {
        String dbDriver = System.getProperty("database.driver");
        String dbDialect = System.getProperty("database.dialect");
        String dbUrl = System.getProperty("database.url");
        String dbUser = System.getProperty("database.user");
        String dbPassword = System.getProperty("database.password");
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, dbDriver);
        settings.put(Environment.DIALECT, dbDialect);
        settings.put(Environment.URL, dbUrl);
        settings.put(Environment.USER, dbUser);
        settings.put(Environment.PASS, dbPassword);
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "validate");

        //C3P0 connection pool settings
        Properties conf = loadConfiguration("c3p0.properties");
        if (!conf.isEmpty()) {
            settings.put(Environment.C3P0_MIN_SIZE, conf.getProperty(Environment.C3P0_MIN_SIZE));
            settings.put(Environment.C3P0_MAX_SIZE, conf.getProperty(Environment.C3P0_MAX_SIZE));
            settings.put(Environment.C3P0_TIMEOUT, conf.getProperty(Environment.C3P0_TIMEOUT));
            settings.put(Environment.C3P0_MAX_STATEMENTS, conf.getProperty(Environment.C3P0_MAX_STATEMENTS));
            settings.put(Environment.C3P0_IDLE_TEST_PERIOD, conf.getProperty(Environment.C3P0_IDLE_TEST_PERIOD));
        }

        return settings;
    }

    private static Properties loadConfiguration(String resourceFileName) {
        Properties configuration = new Properties();
        try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream(resourceFileName)) {
            configuration.load(inputStream);
        }
        catch (Exception e) {
            logger.error(AppConstants.MSG_PREFIX + e.getMessage());
        }

        return configuration;
    }
}