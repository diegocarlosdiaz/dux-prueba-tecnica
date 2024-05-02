FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY *.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]

