## Use the official OpenJDK image for Java 17 as the base image
#FROM openjdk:17-jdk-slim
#
## Set the working directory in the container to the 'target' directory
#WORKDIR /app
#
## Copy the JAR file into the container (assuming it's located in the 'target' directory)
#COPY target/financial-app-0.0.1-SNAPSHOT.jar .
#
## Specify the command to run your Spring Boot application
#CMD ["java", "-jar", "financial-app-0.0.1-SNAPSHOT.jar"]

### STAGE 1: Build ###
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn clean package -DskipTests

### STAGE 2: Run ###
FROM openjdk:17-jdk-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
