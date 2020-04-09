FROM appinair/jdk11-maven:latest
MAINTAINER Konstantin Bandarchyk <kbandarhyk@gmail.com>

ADD target/heroku-web-1.0-SNAPSHOT.jar /home/app/heroku-web-1.0-SNAPSHOT.jar
WORKDIR /home/app

ARG profile

ENTRYPOINT ["java", "-Dspring.profiles.active=$profile", "-jar", "heroku-web-1.0-SNAPSHOT.jar" ]

EXPOSE 8080