FROM openjdk:8-jdk-alpine

ARG VERSAO=1.0.0-SNAPSHOT

ADD ./boot/target/boot-${VERSAO}.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=\"-03:00\"","-jar","/app.jar"]