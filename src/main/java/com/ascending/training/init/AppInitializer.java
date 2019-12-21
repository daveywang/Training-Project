/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.init;

import com.ascending.training.util.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
 * Maven skip test:
 * mvn -Dmaven.test.skip=true clean compile package
 *
 */

/* Define VM options
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
    -Ddatabase.user=admin
    -Ddatabase.password=Training123!
    -Dlogging.level.org.springframework=INFO
    -Dlogging.level.com.ascending=TRACE
    -Dserver.port=8080
    -Dsecret.key=Training123!@#
    -Daws.queue.name=training_queue_ascending_com
    -Dfile.download.dir = /Users/liweiwang/ascending/lecture
*/

/*  Run Spring boot application by mvn command line
    mvn spring-boot:run \
    -Dmaven.test.skip=true \
    -Ddatabase.driver=org.postgresql.Driver \
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect \
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db \
    -Ddatabase.user=admin \
    -Ddatabase.password=Training123! \
    -Dlogging.level.org.springframework=INFO \
    -Dlogging.level.com.ascending=TRACE \
    -Dserver.port=8080 \
    -Dsecret.key=Training123!
    -Daws.queue.name=training_queue_ascending_com
    -Dfile.download.dir = /Users/liweiwang/ascending/lecture
 */

/*
    DefaultAWSCredentialsProviderChain looks for credentials in this order:
    1. Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY ->  EnvironmentVariableCredentialsProvider
    2. Java System Properties - aws.accessKeyId and aws.secretKey -> SystemPropertiesCredentialsProvider
    3. Credential profiles file at the default location (~/.aws/credentials) shared by all AWS SDKs and the AWS CLI -> ProfileCredentialsProvider
    4. Credentials delivered through the Amazon EC2 container service if AWS_CONTAINER_CREDENTIALS_RELATIVE_URI" environment variable is set and security manager has permission to access the variable -> EC2ContainerCredentialsProviderWrapper
    5. Instance profile credentials delivered through the Amazon EC2 metadata service
    6. Web Identity Token credentials from the environment or container.
 */

/*
    If the dependency spring-boot-starter-data-jpa is in pom.xml, the datasource will be configured automatically,
    but it can be disabled by using exclude to exclude all the classes related to the datasource, database connection.
 */
@SpringBootApplication(scanBasePackages = {"com.ascending.training"},
                       exclude = {
                       DataSourceAutoConfiguration.class,
                       DataSourceTransactionManagerAutoConfiguration.class,
                       HibernateJpaAutoConfiguration.class})
@ServletComponentScan(basePackages = {"com.ascending.training.filter"})
public class AppInitializer extends SpringBootServletInitializer {
    public static void main(String[] args) throws NullPointerException{
        if (HibernateUtil.getSessionFactory() == null) {
            throw new NullPointerException("The Hibernate session factory is NULL!");
        }

        SpringApplication.run(AppInitializer.class, args);
    }

    /* Below the code demonstrates the violation of Single Responsibility Principle */
    /*
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 amazonS3() {
        return  AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.US_EAST_2)
                .build();
    }
    */
}
