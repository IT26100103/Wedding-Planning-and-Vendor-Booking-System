@echo off
setlocal

echo ========================================================
echo  Vows ^& Vendors - Guest List ^& RSVP Management Module
echo ========================================================
echo.

set "IDEA_DIR=C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5"
if exist "%IDEA_DIR%\jbr\bin\java.exe" (
    set "JAVA_HOME=%IDEA_DIR%\jbr"
    set "PATH=%IDEA_DIR%\plugins\maven\lib\maven3\bin;%JAVA_HOME%\bin;%PATH%"
)

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Maven not found on PATH.
    pause
    exit /b 1
)

echo Starting Guest ^& RSVP Server at http://localhost:8085/guests
echo Press Ctrl+C to stop the server.
echo.
mvn clean jetty:run
