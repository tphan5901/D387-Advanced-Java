FROM openjdk:11
EXPOSE 8080
COPY target/dockerized-application.jar /usr/src/dockerized-application.jar
WORKDIR /usr/src
ENTRYPOINT ["java", "-jar", "dockerized-application.jar"]