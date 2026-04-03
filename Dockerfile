# Use Java 21 as base image
FROM eclipse-temurin:21-jre-jammy

# Add Maintainer Info
LABEL maintainer="offeria"

# Set application home directory
WORKDIR /app

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Define default environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV CONFIG_SERVER_USER=root
ENV CONFIG_SERVER_PASSWORD=s3cr3t
ENV CONFIG_GIT_URI=https://github.com/your-org/config-repo.git

# Expose Config Server port
EXPOSE 8888

# Healthcheck for container
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8888/actuator/health || exit 1

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
