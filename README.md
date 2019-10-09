# ASCENDING Training course by Liwei Wang

# Branch information

master branch maintains the latest code

hibernate branch maintains the code that uses Hibernate to interact with the database instead of using JDBC. The code is built on the warm-up branch. This branch code is for the third and the fourth classes.

The code demonstrates:
1. how to built Hibernate session factory.
2. how to use code to configure Hibernate instead of using hibernate.cfg.xml or hibernate.properties configuration files.
3. how to create mapping classes.
4. how to use HQL to build DAOs to interact with database. 
5. how to define relationship between entities.
6. the differences between join and join fetch.
7. using entities scanner to add all mapping classes into the Hibernate configuration.

# Database Migration

Uses flyway as migration tool

mvn clean compile flyway:clean flyway:migrate -Ddatabase_url=localhost:port/your_database -Ddatabase_user=your_username -Ddatabase_password=your_password

# About ASCENDING

ASCENDING is an AWS Consulting Select Partner and focuses on AWS with experts having deployed AWS solutions since 2012. We have successfully worked with startups, Mid-size businesses and Education organizations to meet their needs of AWS solutions, custom training and support.

