FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD schedule-1.0.jar schedule.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8081
