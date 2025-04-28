FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/*.jar tv-show-service.jar
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar"]
ENTRYPOINT ["java", "-jar", "tv-show-service"]