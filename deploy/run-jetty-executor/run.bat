@set DIR=%~dp0
@if "%~1"=="" (set IP="localhost") else set IP="%~1"
@if "%~2"=="" (set PORT=9000) else set PORT="%~2"
@set config="%DIR%\..\executor_configs\all_java_config.json"
@echo "Dir: %DIR%"
@echo "Ip: %IP%"
@echo "Port: %PORT%"
@echo "Config path: %config%"
java -Xmx8g ^
--class-path "%DIR%..\SEDE\*";"%DIR%..\SEDE_logging_lib\*";"%DIR%\." ^
de.upb.sede.exec.ExecutorServerStarter %config% %IP% %PORT%
@PAUSE