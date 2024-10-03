FROM openjdk:11
Expose 8080
ADD target/D387_sample_code-0.0.2-SNAPSHOT.jar

ENTRYPOINT ["java", '-jar', "D387_sample_code-0.0.2-SNAPSHOT.jar "]
WORKDIR /usr/src
