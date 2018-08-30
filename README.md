# SEDE

SEDE, short for _Service Execution and Distribution Environment_, provides a service-oriented solution to encapsulate software components and offer their functionality through a communication protocol over a network.

## Quick start

Clone this repository and export SEDE jar files.
``` sh
cd SEDE_ROOT_DIRECTORY
./gradlew jarjar
tree deploy/SEDE
deploy/SEDE
>> ├── SEDE.core-0.0.3.jar
>> ├── SEDE.executor-0.0.3.jar
>> ├── SEDE.gateway-0.0.3.jar
>> ├── SEDE.http-0.0.3.jar
>> ...
```

#### Running a http gateway server:

One-liner (also works on Windows OS):
```sh
# deploy/SEDE/ contains the SEDE jars.
# PATH_TO_LOG_CONFIG is folder that contains log4j2 config.
# The first arguemnt is the port to be used, e.g.: 6000 
# The remaining arguments are enterpreted as file paths to class and type-configurations:
java -cp deploy/SEDE/'*':PATH_TO_LOG_CONFIG \ 
        de.upb.sede.gateway.GatewayServerStarter  \
            6000 \
            deploy/run-gateway-template/configs/* \ # this folder contains all service and type configurations offered by this project.
```

---
Alternatively, one can create a run folder for a specific gateway by copying the template run folder:
```sh
cd SEDE_ROOT_DIRECTORY/deploy
cp -r run-gateway-template my-gateway
tree my-gateway/
>> my-gateway/
>> ├── configs
>> │   ├── demo-classconf.json
>> │   └── demo-typeconf.json
>> └── run.sh
```
Remove the demonstration class- and type-configurations and replace them with the configuration files of the services that should be supported:
``` sh
rm my-gateway/configs/*
cp PATH_TO_CONFIGS/* my-gateway/configs/
tree my-gateway/
>> my-gateway/
>> ├── configs
>> │   ├── a-classconf.json
>> │   ├── a-typeconf.json
>> │   ├── b-classconf.json
>> │   └── b-typeconf.json
>> └── run.sh
```
Optionally, place a Log4j2-configuration file in the run directory:
``` sh
cp ../SEDE.core/src/test/resources/log4j2.xml  my-gateway/
``` 
Run the gateway using the `run.sh` script:
```sh
bash my-gateway/run.sh
```

#### Running a java http executor server:

One-liner (also works on Windows OS):
```sh
# deploy/SEDE/ contains the SEDE jars.
# PATH_TO_LOG_CONFIG is folder that contains log4j2 config.
# Inputs:
# executor-config.json is the path to the executor configuration
# The machine's host address is: 100.90.80.70
# The port opened is: 9000
java -cp deploy/SEDE/'*':path-to-services/'*':PATH_TO_LOG_CONFIG \
    de.upb.sede.exec.ExecutorServerStarter \
        executor-config.json \
        100.90.80.70 \
        9000
```
One can also create a dedicated executor run directory, similarly to how the gateway run directory was created. (Copy and extend the `deploy/run-executor-template` folder instead).

#### Running a python http executor server:

Run `./gradlew buildPython` to build the `deploy/SEDE_python` directory. Each inner folder inside this directory contains the sources of a python sub-project from SEDE in addition to it's `setup.py` file. 
Install the python executor using `pip`:
``` sh
pip install deploy/SEDE_python/SEDE.executor/
```
After this the console command `pyexecutor` is ready. This command takes 3 arguments:

1. Path to the executor configuration file, like this one: [config.json](https://github.com/fmohr/SEDE/blob/develop/SEDE.services/scikit.ml/src/test/resources/executor/config.json)
2. The host machine's address the executor is running on. It is important other executors reach this machine with using this address.
3. The tcp-port that is opened by the http server.

An example:
``` sh
pyexecutor config_path/config.json localhost 5000
```

Before running the executor though, make sure the services you want to use are also installed. For example the sub-project `scikit.ml` offers algorithm from the [scikit-learn library](scikit-learn.org).
The following will install the `scikit.ml` project in addition to its dependencies like `scikit-learn`:
``` sh
pip install deploy/SEDE_python/scikit.ml/
``` 

## Usage example

`TODO`

_For more examples and usage, please refer to the wiki._ `TODO`

## Development setup

SEDE uses Java 8 and Python 3.5 +.
We use Eclipse, IntelliJ IDEA and PyCharm to work on SEDE. These sections show how to setup each development environment.

### Eclipse

Import the gradle project:

1. File > Import...
2. Gradle > Existing Gradle Project
3. Choose the path to your `SEDE_ROOT_DIRECTORY` as the _Project root directory_
4. Finish

There is a set of errors are due to cyclic dependencies between gradle sub-projects. In reality SEDE only contains cyclic dependencies between test modules of sub-projects, so this error is easily fixed by configuring Eclipse:

1. Preferences
2. Java > Compiler > Building
3. Under _Build path problems_ set _Circular dependencies_ to `Warning`.

Now select (right-click) the 'SEDE' project and choose `Refresh gradle project` under Gradle.

### IntelliJ IDEA

Import the gradle project:

1. File > New > Project from existing sources...
2. Choose your `SEDE_ROOT_DIRECTORY`.
3. Import project from external model > Gradle
4. Finish
5. In the Gradle view click the _Refresh all Gradle projects_ button.

### Python

When developing the python modules in SEDE install each python project using pip with _editable mode_ (i.e. setuptools "develop mode") enabled:

``` sh
pip install -e SEDE.executor/src/main/python/
pip install -e SEDE.services/scikit.ml/src/main/python/
```

## Contributing

The usual procedure for contribution to open source projects:
1. Fork it
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request