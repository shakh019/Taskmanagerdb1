# ===== Stage 1: Build =====
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

LABEL maintainer="Talgatbek Sharafatdin <talgatbek.sharafatdin@example.com>"

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Security: non-root user
RUN addgroup -S talgatbek && adduser -S talgatbek -G talgatbek
USER talgatbek

# Copy artifact
COPY --from=builder /app/target/*.jar app.jar

# Create uploads dir
RUN mkdir -p /app/uploads /app/logs

VOLUME ["/app/uploads", "/app/logs"]

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", "app.jar"]
