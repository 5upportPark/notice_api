FROM openjdk:17
LABEL org.opencontainers.image.authors="ParkJiwon"

COPY build/libs/backend_pjw.jar /app.jar

ENV USER_PROFILE local

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=${USER_PROFILE}", "-jar", "/app.jar"]