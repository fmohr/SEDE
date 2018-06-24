#!/bin/bash

# directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

java -cp SEDE-1.0.jar de.upb.sede.deployment.ConfigUploader "$@"
