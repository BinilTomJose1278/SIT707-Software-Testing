# Use a lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine

# Copy the compiled jar file from target directory
COPY target/student-gradebook-api-0.0.1-SNAPSHOT.jar app.jar

# Expose port used by the application
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
