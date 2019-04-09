#!/bin/bash

CPU_ONLY='false'

while getopts 'c' flag; do
    case "${flag}" in
        c) CPU_ONLY='true' ;;
    esac
done


# create symlinks for gradle and SEDE.core
cd ${SEDE_ROOT}
rm -rf SEDE.services/c2.image/libs/CServices.jar
ln -sf ${SEDE_ROOT}/gradlew ${SEDE_ROOT}/gradle/gradle
mkdir sede
ln -sf ${SEDE_ROOT}/deploy/SEDE/SEDE.core-0.0.10.jar sede/SEDE.core-0.0.10.jar
PATH=$PATH:${SEDE_ROOT}/gradle

# build CServices
rm -rf CServices ServiceCodeProvider
git clone http://gitlab+deploy-token-4:yCTsCsDCsGH9B7spAhxk@git.cs.upb.de/SFB901-Testbed/ServiceCodeProvider.git --branch develop
find . -type f -name '*.sh' | xargs chmod +x
# mv ServiceCodeProvider/CServices .
# rm -rf ServiceCodeProvider
cd ServiceCodeProvider/CServices
if [ $CPU_ONLY = 'true' ] ; then
    ./compile.sh -c
else
    ./compile.sh
fi

# copy .jar file to c2.image/libs
cp -r ./build/libs/. ${SEDE_ROOT}/SEDE.services/c2.image/libs/
cd ../..
mv ServiceCodeProvider/CServices ./
rm -rf ServiceCodeProvider

# clean
rm -rf sede
unlink ${SEDE_ROOT}/gradle/gradle