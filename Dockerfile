# Multi-stage build for both frontend and backend

# Stage 1: Build frontend
FROM node:18-alpine AS frontend-build
WORKDIR /app/frontend

# Copy package files first for better caching
COPY frontend/package*.json ./
RUN npm ci

# Copy frontend source
COPY frontend/src ./src
COPY frontend/public ./public
COPY frontend/tsconfig.json ./

# Build the frontend
RUN npm run build

# List contents for debugging
RUN ls -la /app/frontend/build || echo "Build directory not found"

# Stage 2: Build backend
FROM maven:3.8.4-openjdk-17 AS backend-build
WORKDIR /app
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src
WORKDIR /app/backend
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from backend build stage
COPY --from=backend-build /app/backend/target/simulator-backend-0.0.1-SNAPSHOT.jar app.jar

# Copy the built frontend from frontend build stage
COPY --from=frontend-build /app/frontend/build ./static

# Expose the port
EXPOSE 8080

# Set environment variable for port
ENV PORT=8080

# Run the application
CMD ["java", "-jar", "app.jar"]
