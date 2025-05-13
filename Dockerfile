FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/*.jar tv-show-service.jar
COPY wait-for-mysql.sh wait-for-mysql.sh

RUN apt-get update && apt-get install -y netcat-openbsd  && chmod +x wait-for-mysql.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-mysql.sh", "mysql", "java", "-jar", "tv-show-service.jar"]