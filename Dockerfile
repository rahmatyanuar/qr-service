FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} qr-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/qr-service-0.0.1-SNAPSHOT.jar"]