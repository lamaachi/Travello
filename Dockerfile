# This Dockerfile packages the .jar file you already built locally.

# Use a lightweight Java runtime image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Corrected COPY command:
# Remove "--from=build" to copy from your local machine's "target" folder.
COPY target/Travel_Agency_pfe-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8081

# The command to run your application
ENTRYPOINT ["java","-jar","app.jar"]