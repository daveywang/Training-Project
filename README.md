# ASCENDING Training course by Liwei Wang

# Branch information

master branch maintains the latest code

warm-up branch maintains the java models, jdbc, unit test. This branch code is for the first two classes.

The code demonstrates:
1. how to access Database by using JDBC.
2. how to use flyway to make changes on the database.
3. how to use maven to compile, test the code.
4. how to use logger to log different level information. 
5. how to do unit test by using Junit.

# Database Migration

Uses flyway as migration tool

mvn clean compile flyway:clean flyway:migrate -Ddatabase_url=localhost:port/your_database -Ddatabase_user=your_username -Ddatabase_password=your_password

# About ASCENDING

ASCENDING is an AWS Consulting Select Partner and focuses on AWS with experts having deployed AWS solutions since 2012. We have successfully worked with startups, Mid-size businesses and Education organizations to meet their needs of AWS solutions, custom training and support.

