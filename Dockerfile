FROM openjdk:11

COPY target/training-0.0.1-SNAPSHOT.jar training.jar
ENTRYPOINT ["java","-jar", "training.jar"]
