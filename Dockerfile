FROM openjdk:8-jdk-alpine
COPY target/joke-api-0.0.1.jar joke-api
ENTRYPOINT ["java","-jar","/joke-api.jar"]