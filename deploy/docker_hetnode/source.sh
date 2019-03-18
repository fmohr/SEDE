#!/bin/sh

PATH=/usr/local/cuda-8.0/bin:$PATH
LD_LIBRARY_PATH=/usr/local/cuda-8.0/lib64:/usr/lib64/nvidia:$LD_LIBRARY_PATH

PGI=/opt/pgi/
PGIVER=18.4/
PATH=$PGI/linux86-64/$PGIVER/bin:$PATH

PATH=/opt/maxeler/maxeleros/utils:$PATH
LD_LIBRARY_PATH=/opt/maxeler/maxeleros/lib:$LD_LIBRARY_PATH

export PGI
export PATH
export LM_LICENSE_FILE=27000@license1.uni-paderborn.de:$LM_LICENSE_FILE
export LD_LIBRARY_PATH
