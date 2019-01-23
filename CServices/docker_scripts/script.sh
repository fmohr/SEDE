#!/bin/bash
yum -y update
yum -y groupinstall "Development Tools"
yum -y install java-1.8.0-openjdk java-1.8.0-openjdk-devel git nano vim ImageMagick ImageMagick-devel tmux

source /root/script/source.sh
cd /opt/maxeler/maxeleros/daemon && ./maxelerosd

cd /root && git clone "https://git.cs.upb.de/aloesch/sfb901_demonstrator.git"
cd /root/sfb901_demonstrator/service_node/build && ./make_all
cd /root/sfb901_demonstrator/service_caller/build && make
cd /root/sfb901_demonstrator/service_deployment/build && make
cd /root/sfb901_demonstrator/service_plugins/build && ./unpack_maxfiles && ./make_all_dfe

bash

