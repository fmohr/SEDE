#!/bin/bash
# directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# find the next free port
export port=9000
while lsof -Pi :"$port" -sTCP:LISTEN -t >/dev/null ; do
    echo port "$port" is not open
    port=$((port + 1))
done
echo Port: "$port"

java -cp "$DIR"/../SEDE-1.0.jar de.upb.sede.gateway.GatewayServerStarter "$port"
