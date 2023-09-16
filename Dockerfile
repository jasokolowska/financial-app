# Use the official OpenJDK image for Java 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container to the 'target' directory
WORKDIR /app

# Copy the JAR file into the container (assuming it's located in the 'target' directory)
COPY target/financial-app-0.0.1-SNAPSHOT.jar .

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "financial-app-0.0.1-SNAPSHOT.jar"]

