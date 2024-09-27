FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/to_do_list-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
