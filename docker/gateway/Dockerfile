FROM sede:java

RUN apt-get update && apt-get install -y --no-install-recommends \
    graphviz \
    ttf-freefont
RUN export DOT_PATH=$(which dot)

COPY ./configs /configs