# Builder
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
EXPOSE 5050