FROM openjdk:14-jdk-alpine

MAINTAINER Henrique

ADD ./build/libs/todo-gateway-0.0.1-SNAPSHOT.jar /opt/todo-gateway.jar
CMD java -jar -Dspring.profiles.active=$PROFILE /opt/todo-gateway.jar