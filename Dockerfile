FROM gradle:8.10.2-jdk17-focal AS builder
RUN pwd
COPY . /app/Itopw
WORKDIR /app/Itopw
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17.0-jdk-slim
COPY --from=builder /app/Itopw/build/libs/*.jar app.jar
COPY --from=builder /app/Itopw/src/main/resources/static/images /app/images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]
