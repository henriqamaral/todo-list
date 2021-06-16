FROM openjdk:14-jdk-alpine

MAINTAINER Henrique

ADD ./build/libs/todo-discovery-0.0.1-SNAPSHOT.jar /opt/todo-discovery.jar
CMD java -jar -Dspring.profiles.active=$PROFILE /opt/todo-discovery.jar