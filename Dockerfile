FROM openjdk:8-jdk-alpine

WORKDIR /user-service
COPY target/user-service-*.jar /user-service/
RUN chmod +x /user-service/user-service*.jar
ENTRYPOINT java -jar user-service*.jar
#ENTRYPOINT ["sh", "-c", "sleep 1d"]