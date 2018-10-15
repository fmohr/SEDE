@set DIR=%~dp0
@if "%~1"=="" (set PORT=6000) else set PORT="%~1"
@echo "Dir: %DIR%"
@echo "Port: %PORT%"
java --class-path "%DIR%..\SEDE\*";"%DIR%..\SEDE_logging_lib\*";"%DIR%\."  de.upb.sede.gateway.GatewayServerStarter %PORT% ^
        config/builtin-classconf.json ^
        config/builtin-typeconf.json ^
        config/c2imaging-classconf.json ^
        config/c2imaging-typeconf.json ^
        config/imaging-classconf.json ^
        config/imaging-typeconf.json ^
        config/sl-ml-classifiers-classconf.json ^
        config/sl-ml-typeconf.json ^
        config/weka-ml-classifiers-classconf.json ^
        config/weka-ml-clusterers-classconf.json ^
        config/weka-ml-pp-classconf.json ^
        config/weka-ml-typeconf.json
@PAUSE