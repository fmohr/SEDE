#!/bin/bash
export SEDE_ROOT=${PWD}
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/csrc/service_plugins/build/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:/opt/maxeler/maxeleros/lib/
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${SEDE_ROOT}/CServices/max_so/
