FROM eclipse-temurin:24-jdk

# Copia el JAR generado (usa el nombre exacto)
COPY target/ia-0.0.1-SNAPSHOT.jar app.jar



ENTRYPOINT ["java", "-jar", "/app.jar"]
