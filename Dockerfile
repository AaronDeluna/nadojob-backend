FROM openjdk:17-slim
WORKDIR /app
COPY nadojob-backend-1.0.0.jar /app/nadojob-backend.jar
CMD ["java", "-jar", "nadojob-backend.jar"]