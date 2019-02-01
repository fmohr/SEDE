#!/bin/bash
yum -y update
yum -y groupinstall "Development Tools"
yum -y install java-1.8.0-openjdk java-1.8.0-openjdk-devel git nano vim ImageMagick ImageMagick-devel tmux

source /root/script/source.sh
cd /opt/maxeler/maxeleros/daemon && ./maxelerosd

#cd /root && git clone "https://git.cs.upb.de/aloesch/sfb901_demonstrator.git"
#cd /root/sfb901_demonstrator/service_node/build && ./make_all
#cd /root/sfb901_demonstrator/service_caller/build && make
#cd /root/sfb901_demonstrator/service_deployment/build && make
#cd /root/sfb901_demonstrator/service_plugins/build && ./unpack_maxfiles && ./make_all_dfe


#COPY ./run-gateway-template/log4j2.xml /log4j2.xml
#COPY ./SEDE /SEDE
#COPY ./SEDE_logging_lib /SEDE_logging_lib

#COPY ./executor_configs/all_java_config.json /config.json
#ENV EXECUTOR_ADDRESS="localhost:6001"

# set out environment.
export SEDE_ROOT=/root/SEDE
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/csrc/service_plugins/build/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:/opt/maxeler/maxeleros/lib/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/max_so/

export EXECUTOR_ADDRESS="cc-8.pc2.upb.de:9000"
export PROXY_ADDRESS="131.234.58.16:9090"

DIR="/root/SEDE/deploy/run-executor-template"

#config="${PWD##*/}"/config.json
#config="$DIR"/config.json
config="$DIR"/../executor_configs/all_cservices_docker_config.json
echo Configuration file: "$config"

java -Xmx8g -cp "$DIR"/../SEDE/'*':"$DIR"/../SEDE_logging_lib/'*':"$DIR"/services/'*':"$DIR"  de.upb.sede.exec.ExecutorServerStarter "$config" cc-8.pc2.upb.de 9000

#ENTRYPOINT java -Xmx12g -cp SEDE/*:SEDE_logging_lib/*:. de.upb.sede.exec.ExecutorServerStarter config.json localhost 80

bash

