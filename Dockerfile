# Use the official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the backend pom.xml and source code
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src

# Build the application
WORKDIR /app/backend
RUN mvn clean package -DskipTests

# Use a lighter image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/backend/target/simulator-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Set environment variable for port
ENV PORT=8080

# Run the application
CMD ["java", "-jar", "app.jar"]
