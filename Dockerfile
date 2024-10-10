FROM openjdk:11
EXPOSE 8080
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar /usr/src/D387_sample_code-0.0.2-SNAPSHOT.jar
WORKDIR /usr/src
ENTRYPOINT ["java", "-jar", "D387_sample_code-0.0.2-SNAPSHOT.jar"]