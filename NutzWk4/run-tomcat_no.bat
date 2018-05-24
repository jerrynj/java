@echo off
title %cd%
echo.
echo [信息] 使用Tomcat插件运行工程。
echo.
rem pause
rem echo.

cd %~dp0
cd wk-app
cd wk-web

set MAVEN_OPTS=%MAVEN_OPTS% -Xms256m -Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m
call mvn tomcat:run

cd bin
pause