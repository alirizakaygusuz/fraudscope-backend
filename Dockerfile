FROM openjdk:17

LABEL maintainer="Ali Riza Kaygusuz"
LABEL description="FraudScope Spring Boot Application"

WORKDIR /app

COPY target/fraudscope-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java" , "-jar" ,"app.jar"]