FROM openjdk:11-jdk-slim
COPY target/*.jar app.jar

CMD ["java","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]
EXPOSE 8080