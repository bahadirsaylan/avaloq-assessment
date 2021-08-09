FROM openjdk:8-jre-slim

ENV JAR_NAME avaloq-api.jar
ADD $JAR_NAME /opt/build/

EXPOSE 8080

WORKDIR /opt/build/
CMD java $JVM_ARGS -jar $JAR_NAME