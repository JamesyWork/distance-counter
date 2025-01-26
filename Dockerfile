FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/distanceCounter.jar

EXPOSE 8081

RUN ls /app

CMD ["java", "-jar", "/app/distanceCounter.jar"]