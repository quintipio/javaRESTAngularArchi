FROM openjdk:8-jdk-alpine
MAINTAINER quintipio
VOLUME /tmp
ADD target/javarestangulararchi-0.0.8-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom -jar /app.jar
