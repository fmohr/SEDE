#!/bin/bash
configs="configs"

java -cp SEDE-1.0.jar de.upb.sede.deployment.ConfigUploader "$1" $2
