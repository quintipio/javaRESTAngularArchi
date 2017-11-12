FROM openjdk:8-jdk-alpine
MAINTAINER quintipio

VOLUME /tmp
RUN mvn package -Dmaven.test.skip=true
ADD target/javarestangulararchi-0.0.8-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom -jar /app.jar
