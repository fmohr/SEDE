#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

configs="configs"

java -cp "$DIR"/SEDE/'*' de.upb.sede.deployment.ConfigUploader "$1" $2
