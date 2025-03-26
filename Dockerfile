# Stage 1: Build the application with Gradle and JDK 17
FROM gradle:7.6-jdk17 AS builder
WORKDIR /home/gradle/project

# Copy all project files into the container
COPY . .

# Clean and build the project while skipping tests (-x test)
RUN gradle clean build -x test --no-daemon --refresh-dependencies

# Stage 2: Deploy the application using Tomcat (JRE 11)
FROM tomcat:9-jre11
WORKDIR /usr/local/tomcat/webapps

# Remove the default ROOT web application
RUN rm -rf ROOT

# Copy the WAR file built in Stage 1 and rename it to demo.war so it deploys under the /demo context
COPY --from=builder /home/gradle/project/build/libs/*.war ./demo.war

# Expose port 8080 so the application can be accessed externally
EXPOSE 8080
