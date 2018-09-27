#!/bin/bash
# directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# look up the ip address:
ip=$(ip route get 8.8.8.8 | awk 'NR==1 {print $NF}')
echo IP address: "$ip"

# find the next free port
export port=9000
while lsof -Pi :"$port" -sTCP:LISTEN -t >/dev/null ; do
    echo port "$port" is not open
    port=$((port + 1))
done
echo Port: "$port"

#config="${PWD##*/}"/config.json
# config="$DIR"/config.json
config="$DIR"/../executor_configs/all_java_config.json
echo Configuration file: "$config"

java -cp "$DIR"/../SEDE/'*':"$DIR"/services/'*':"$DIR"  de.upb.sede.exec.ExecutorServerStarter "$config" "$ip" "$port"