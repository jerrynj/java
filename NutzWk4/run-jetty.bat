@echo off
title %cd%
echo.
echo [��Ϣ] ʹ��Jetty������й��̡�
echo.
rem pause
rem echo.

cd %~dp0
cd wk-app
cd wk-web

set MAVEN_OPTS=%MAVEN_OPTS% -Xms256m -Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m
call mvn jetty:run

pause