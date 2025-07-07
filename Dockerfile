# Usa Java 21
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
