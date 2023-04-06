FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -Dmaven.test.skip

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
FROM eclipse-temurin:17.0.6_10-jre-alpine
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]