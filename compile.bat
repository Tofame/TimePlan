@echo off
mvn clean install
if ERRORLEVEL 1 (
    echo Build failed. Exiting...
    pause
    exit /b 1
)
echo Starting Spring Boot application...
call mvn spring-boot:run

pause