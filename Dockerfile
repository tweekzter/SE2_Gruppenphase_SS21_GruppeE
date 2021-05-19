FROM openjdk:11

WORKDIR /server

COPY ./app/src/main/java/com ./

RUN cd com/example/se2_gruppenphase_ss21/networking && \
    javac *.java client/*.java server/*.java server/logic/*.java

COPY docker-entrypoint.sh ./

RUN chmod +x ./docker-entrypoint.sh

ENTRYPOINT ["./docker-entrypoint.sh" ]