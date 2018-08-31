FROM sede:java

RUN apt-get update && apt-get install -y --no-install-recommends \
    graphviz \
    ttf-freefont

COPY ./configs /configs

ENV configfiles = /configs