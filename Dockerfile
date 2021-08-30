FROM openjdk:8-jdk-alpine
COPY target/trip2.0.jar trip2.0.jar
ENTRYPOINT ["java", "-jar", "/trip2.0.jar"]