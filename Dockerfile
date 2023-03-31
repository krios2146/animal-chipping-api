FROM maven:3.8.4-openjdk-17 AS build

ARG SPRING_DATASOURCE_URL
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

ENV SPRING_DATASOURCE_URL $SPRING_DATASOURCE_URL
ENV POSTGRES_USER $POSTGRES_USER
ENV POSTGRES_PASSWORD $POSTGRES_PASSWORD

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
