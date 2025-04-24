# Use an OpenJDK image as the base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container (for a traditional build)
COPY target/ms_rating-0.0.1-SNAPSHOT.jar ms_rating-0.0.1-SNAPSHOT.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "ms_rating-0.0.1-SNAPSHOT.jar"]

