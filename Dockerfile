FROM openjdk:14-jdk-alpine

MAINTAINER Henrique

ADD ./build/libs/todo-0.0.1-SNAPSHOT.jar /opt/todo.jar
CMD java -jar -Dspring.profiles.active=$PROFILE /opt/todo.jar