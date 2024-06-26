FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ./target/front-0.0.1-SNAPSHOT.jar TxT-front.jar
ENTRYPOINT ["java", "-jar", "/TxT-front.jar"]
