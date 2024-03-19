FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle

COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

RUN chmod +x gradlew

RUN ./gradlew build --scan --debug
