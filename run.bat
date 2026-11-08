@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0"

echo ===============================================================================
echo Starting Wedding Planning and Vendor Booking System on http://localhost:8085
echo ===============================================================================

rem Ensure data directory exists
if not exist "%USERPROFILE%\wedding-data" (
    mkdir "%USERPROFILE%\wedding-data"
    echo Created %USERPROFILE%\wedding-data
)

rem Copy initial sample data if missing
if exist "sample-data" (
    for %%f in (sample-data\*.txt) do (
        if not exist "%USERPROFILE%\wedding-data\%%~nxf" (
            copy "%%f" "%USERPROFILE%\wedding-data\%%~nxf" >nul
            echo Seeded %%~nxf into %USERPROFILE%\wedding-data
        )
    )
)

rem Check Java
where java >nul 2>&1
if errorlevel 1 (
    if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\jbr" (
        set "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\jbr"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
    ) else if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.13.9-hotspot" (
        set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.13.9-hotspot"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
    ) else if exist "%ProgramFiles%\Java\jdk-21" (
        set "JAVA_HOME=%ProgramFiles%\Java\jdk-21"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
    )
)

rem Check Maven
set "MVN_CMD=mvn"
where mvn >nul 2>&1
if errorlevel 1 (
    if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\plugins\maven\lib\maven3\bin\mvn.cmd" (
        set "MVN_CMD=C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\plugins\maven\lib\maven3\bin\mvn.cmd"
    ) else if exist "%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.11-bin\6mqf5t809d9geo83kj4ttckcbc\apache-maven-3.9.11\bin\mvn.cmd" (
        set "MVN_CMD=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.11-bin\6mqf5t809d9geo83kj4ttckcbc\apache-maven-3.9.11\bin\mvn.cmd"
    ) else if exist "%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd" (
        set "MVN_CMD=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"
    )
)

echo Starting Jetty web server on port 8085...
echo Open your browser at: http://localhost:8085/
echo (Press Ctrl+C to stop the server)
echo -------------------------------------------------------------------------------

"%MVN_CMD%" jetty:run
pause
