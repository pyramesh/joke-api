## Application guide
## Technologies used to implement the business use case

1. Java 8
2. Spring Boot 2.7.14
3. Lombok
4. Spring Cloud OpenFeign
5. Junit 5 - unit tests
6. Wiremock - Integration tests
7. Maven       - Build the project
8. OpenAPI     - REST API documentation

## How to build and run the application

1. Prerequisite: Java 8, and Maven should be available to build the application.
2. Open command prompt in the root folder of the project where POM.xml exists.
3. Execute the below commands. this will build the project and executes all unit tests and integration test cases.
   ```
   $ mvn clean install
   ``` 
   To run the test cases.
    ```
   $ mvn test
     ```   
4. Once build is success, Jar (joke-api-0.0.1.jar) will be generated under the /target folder
   (Example: joke-api/target/joke-api-0.0.1.jar)
5. Open command prompt in target folder and execute the below command to run the application.

   ```
   $ java -jar joke-api-0.0.1.jar
   ``` 
6. And then logs shows that the application is started successfully like 'Tomcat started on port(s): 8888 (http) 
7. Open the below link to access REST API documentation.

   http://localhost:8888/swagger-ui/index.html
