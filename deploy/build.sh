#!/bin/bash
cd .. # switch to root dir
gradle clean assemble ':SEDE.http-apps:buildJar'