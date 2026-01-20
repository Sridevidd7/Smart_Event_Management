@echo off
REM Smart Event Management System - Web Application Launcher
REM This script runs the Spring Boot web application

echo.
echo ========================================
echo Smart Event Management System
echo Web Version (Spring Boot)
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java 17 or higher is not installed!
    echo Please install Java 17+ from https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

REM Check if JAR file exists
if not exist "target\smartevent-1.0-SNAPSHOT.jar" (
    echo ERROR: JAR file not found!
    echo Please run: mvn clean package
    pause
    exit /b 1
)

echo Starting application...
echo.
echo ========================================
echo Prerequisites:
echo - MySQL server running on localhost:3306
echo - Database: event_club_db
echo - Credentials: root / Sridevi@2006
echo ========================================
echo.
echo The application will be available at:
echo   http://localhost:8080
echo.
echo Press CTRL+C to stop the application
echo.

REM Run the JAR file
java -jar target\smartevent-1.0-SNAPSHOT.jar

pause
