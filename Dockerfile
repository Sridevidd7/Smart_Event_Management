# Multi-stage build for Java Swing application
# Stage 1: Build
FROM maven:3.8.4-openjdk-17-slim as builder

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/smartevent-*.jar app.jar

# Install MySQL client for database connectivity
RUN apt-get update && apt-get install -y \
    mysql-client \
    && rm -rf /var/lib/apt/lists/*

# Expose port for application
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD java -version || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
