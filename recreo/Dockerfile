FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/recreo-0.0.1.jar
COPY ${JAR_FILE} app_recreo.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app_recreo.jar"]