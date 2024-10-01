FROM openjdk:11
Expose 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", '-jar', "spring-boot-docker.jar"]
WORKDIR /usr/src
