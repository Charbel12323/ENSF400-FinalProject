#1 Building the application
FROM gradle:7.6-jdk8 as builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean build --no-daemon

#2 Updated the docker file to package the application
FROM openjdk:8-jre-alpine
WORKDIR /app

