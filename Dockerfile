FROM openjdk:17
LABEL authors="bhola"
COPY target/billing-funding-service-0.0.1-SNAPSHOT.jar  /app/billing-funding-service.jar
EXPOSE 9099
ENTRYPOINT ["java", "-jar", "/app/billing-funding-service.jar"]