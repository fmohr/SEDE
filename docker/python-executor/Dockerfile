FROM python:3.6-stretch
COPY bin /bin
RUN find /bin -maxdepth 1 -mindepth 1 -type d -exec pip install {} \;

COPY executor_config.json config.json

ARG address
ENV address='localhost'

ARG port
ENV port=5000
EXPOSE ${port}/tcp

CMD exec pyexecutor config.json "$address" "$port"