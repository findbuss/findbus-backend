# Dockerfile para projeto Kotlin Spring Boot JVM 21

FROM eclipse-temurin:21-jdk as build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]