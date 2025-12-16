@echo off
REM Smart Event Management System - Run Script
REM This script builds and runs the application

echo ========================================
echo Smart Event Management System
echo ========================================
echo.

echo [1/3] Cleaning previous builds...
call mvn clean

echo.
echo [2/3] Compiling source code...
call mvn compile

echo.
echo [3/3] Running application...
call mvn exec:java

pause
