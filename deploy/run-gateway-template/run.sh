#!/bin/bash
# directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# find the next free port
export port=6000
while lsof -Pi :"$port" -sTCP:LISTEN -t >/dev/null ; do
    echo port "$port" is not open
    port=$((port + 1))
done
echo Port: "$port"

java -cp "$DIR"/../SEDE/'*':"$DIR"/../SEDE_logging_lib/'*':"$DIR"  de.upb.sede.gateway.GatewayServerStarter "$port" \
        config/builtin-classconf.json \
        config/builtin-typeconf.json \
        config/c2imaging-classconf.json \
        config/c2imaging-typeconf.json \
        config/imaging-classconf.json \
        config/imaging-typeconf.json \
        config/sl-ml-classifiers-classconf.json \
        config/sl-ml-typeconf.json \
        config/weka-ml-classifiers-classconf.json \
        config/weka-ml-clusterers-classconf.json \
        config/weka-ml-pp-classconf.json \
        config/weka-ml-typeconf.json \
