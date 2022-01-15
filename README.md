# Software Developers Training Course by Liwei Wang
* The purpose of this project is for training, which demonstrates the technologies used in backend web application. 
* Please note: The code can be used freely, but **PLEASE KEEP THE LICENSE INFORMATION** at the top of the programs. 
* **THE CODE CAN NOT BE USED FOR COMMERCIAL PURPOSE.**

# The technologies and tools used 
* Frameworks: Spring/Spring Boot, Hibernate.
* Tools: Entity scanner, JUnit, Mockito, Docker, Flyway, Maven, Git, Logger, Postman.
* Database: Postgres.
* The third-party API integration: AWS S3, AWS SQS and SendGrid.
* OOD Principles: DI, Design patterns and S.O.L.I.D. principles are applied. 
* Application Security: JWT and Filter are used for Authentication and Authorization. 
* Servlet, Listener are implemented for the demonstration.
* Redis for caching
* Centralized and customized exception handling
* Implemented the Object Properties Filter that can hide properties(data) of objects dynamically based on the user's role.

# Branch information

* [master branch](https://github.com/daveywang/Training-Project/tree/master) maintains the latest code
* [warmup_branch](https://github.com/daveywang/Training-Project/tree/warm-up) maintains the java models, jdbc, unit test
* [hibernate_branch](https://github.com/daveywang/Training-Project/tree/hibernate) maintains hibernate implementation
* [springBoot_branch](https://github.com/daveywang/Training-Project/tree/spring-boot) maintains controllers implementation
* [jwt_branch](https://github.com/daveywang/Training-Project/tree/jwt) maintains security implementation
* [aws-s3-sqs_branch](https://github.com/daveywang/Training-Project/tree/aws-s3-sqs)  maintains aws and third party implementation

# JVM options
* Configurations are defined through JVM Options.

-Ddatabase.driver=org.postgresql.Driver

-Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect

-Ddatabase.url=jdbc:postgresql://localhost:5432/<Database Name>

-Ddatabase.user=User Name

-Ddatabase.password=User Password

-Dlogging.level.org.springframework=INFO

-Dlogging.level.org.liwei=TRACE

-Dserver.port=8080

-Dsecret.key=Token Key

-Daws.accessKeyId=Your AWS Access Key

-Daws.secretKey=Your AWS secret Key

-Daws.queue.name=Your Queue Name

-Dfile.download.dir=The file location for download

-XX:AutoBoxCacheMax=1024

-Xmn1G

-Xmx1G
