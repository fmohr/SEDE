FROM openjdk:8

COPY ./config.json /config.json
COPY ./log4j2.xml /log4j2.xml
COPY ./bin /SEDE

ARG address
ENV address='localhost'

ARG port
ENV port=9000
EXPOSE ${port}/tcp

CMD exec java -cp SEDE/'*':.  de.upb.sede.exec.ExecutorServerStarter /config.json "$address" "$port"