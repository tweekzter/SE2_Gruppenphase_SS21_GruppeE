FROM openjdk:11-jdk-slim

WORKDIR /server

COPY ./app/src/main/java ./

RUN cd com/example/se2_gruppenphase_ss21/networking && \
    javac *.java server/*.java server/logic/*.java

COPY docker-entrypoint.sh ./

RUN chmod +x ./docker-entrypoint.sh

ENTRYPOINT ["./docker-entrypoint.sh" ]