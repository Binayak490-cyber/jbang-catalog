@echo off
rem Copyright Debezium Authors.
rem Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

if "%JAVA_HOME%"=="" (
  set JAVA_BINARY=java
) else (
  set JAVA_BINARY=%JAVA_HOME%\bin\java
)

for %%f in (debezium-server-*runner.jar) do set RUNNER=%%f
if "%RUNNER%"=="" (
  echo ERROR: runner jar not found
  exit /b 1
)

%JAVA_BINARY% --add-opens java.base/java.lang=ALL-UNNAMED %JAVA_OPTS% -cp "%RUNNER%;conf;lib\*" io.debezium.server.Main %*
