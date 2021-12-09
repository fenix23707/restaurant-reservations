FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

COPY ./target/restaurant-reservations.jar /home/app.jar
ADD ./docker-entrypoint.sh docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/app.jar"]
