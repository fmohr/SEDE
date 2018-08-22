FROM openjdk:8

COPY ./configs /configs
COPY ./log4j2.xml /log4j2.xml
COPY ./bin /SEDE

ARG port
ENV port=6000
EXPOSE ${port}/tcp

CMD exec java -cp SEDE/'*':.  de.upb.sede.gateway.GatewayServerStarter "$port" /configs/*