# ASCENDING Training course by Liwei Wang

# Branch information

master branch maintains the latest code

aws-s3-sqs branch maintains the code that uses AWS SDK to implement AWS S3 and AWS SQS functionalities. The code is built on the jwt branch. This branch code is for the ninth and the tenth training classes.

The code demonstrates:
1. how to integrate AWS S3 service.
2. how to integrate AWS SQS service.
3. how to implement file upload controller by using Spring Boot.
4. how to implement file download controller by uing Spring Boot.

# Database Migration

Uses flyway as migration tool

mvn clean compile flyway:clean flyway:migrate -Ddatabase_url=localhost:port/your_database -Ddatabase_user=your_username -Ddatabase_password=your_password

# About ASCENDING

ASCENDING is an AWS Consulting Select Partner and focuses on AWS with experts having deployed AWS solutions since 2012. We have successfully worked with startups, Mid-size businesses and Education organizations to meet their needs of AWS solutions, custom training and support.

