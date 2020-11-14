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

jwt branch maintains the code that uses JWT to implement authentication and authorization for Restful API. The code is built on the spring-boot branch. This branch code is for the seventh and the eighth training classes.

The code demonstrates:
1. how to secure your Restful API by using JWT and filter.
2. how to implement authentication for Restful API.
3. how to implement authorization for Restful API.

# Database Migration

Uses flyway as migration tool

mvn clean compile flyway:clean flyway:migrate -Ddatabase_url=localhost:port/your_database -Ddatabase_user=your_username -Ddatabase_password=your_password


