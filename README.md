# Software Developers Training Course by Liwei Wang
* The purpose of this project is for training, which demonstrates the technologies used in backend web application. 
* Please note: The code can be used freely, but ![#f03c15](https://placehold.it/13/f03c15/000000?text=+) **PLEASE KEEP THE LICENSE INFORMATION** ![#f03c15](https://placehold.it/13/f03c15/000000?text=+) at the top of the programs. 
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


