FROM eclipse-temurin:17.0.6_10-jre-alpine
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]