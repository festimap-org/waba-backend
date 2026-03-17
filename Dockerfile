FROM eclipse-temurin:17-jre

WORKDIR /app

COPY build/libs/eventer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9998
EXPOSE 7070

ENTRYPOINT ["java","-Xms256m","-Xmx512m","-jar","/app/app.jar"]