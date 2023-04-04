FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar Map-of-attractions-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Map-of-attractions-0.0.1-SNAPSHOT.jar"]