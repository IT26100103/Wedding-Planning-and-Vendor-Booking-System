@echo off
setlocal
echo ===============================================================================
echo Starting Feedback and Review Management on http://localhost:8086
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

rem Detect Java 21
if exist "C:\Users\moham\.jdks\ms-21.0.7" (
    set "JAVA_HOME=C:\Users\moham\.jdks\ms-21.0.7"
)

rem Run with Jetty Maven plugin
set "MVN_CMD=mvn"
if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\plugins\maven\lib\maven3\bin\mvn.cmd" (
    set "MVN_CMD=C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.5\plugins\maven\lib\maven3\bin\mvn.cmd"
)

echo Running: %MVN_CMD% jetty:run
"%MVN_CMD%" jetty:run
pause
