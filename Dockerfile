# Use OpenJDK 21 LTS
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Create a folder for H2 database
RUN mkdir -p /app/data

# Copy the built jar into container
COPY target/invoice-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
