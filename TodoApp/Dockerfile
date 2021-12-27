FROM openjdk:8-jre-slim-buster
MAINTAINER onur.akbal
COPY target/TodoApp-0.0.1-SNAPSHOT.jar TodoApp.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar","/TodoApp.jar"]