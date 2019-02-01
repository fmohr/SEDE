#!/bin/bash
# directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# look up the ip address:
#ip=$(ip route get 8.8.8.8 | awk 'NR==1 {print $NF}')
ip="localhost"
echo IP address: "$ip"

# find the next free port
export port=9000
while lsof -Pi :"$port" -sTCP:LISTEN -t >/dev/null ; do
    echo port "$port" is not open
    port=$((port + 1))
done
echo Port: "$port"

# set out environment.
export SEDE_ROOT=${DIR}/../../
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/csrc/service_plugins/build/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:/opt/maxeler/maxeleros/lib/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/max_so/

export EXECUTOR_ADDRESS="cc-8.pc2.uni-paderborn.de:9000"
export PROXY_ADDRESS="131.234.58.16:9090"

#config="${PWD##*/}"/config.json
#config="$DIR"/config.json
config="$DIR"/../executor_configs/all_cservices_config.json
echo Configuration file: "$config"

java -Xmx8g -cp "$DIR"/../SEDE/'*':"$DIR"/../SEDE_logging_lib/'*':"$DIR"/services/'*':"$DIR"  de.upb.sede.exec.ExecutorServerStarter "$config" "$ip" "$port"
