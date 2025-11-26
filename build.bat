@echo off
REM ═══════════════════════════════════════════════════════════════════════════
REM Build script for Flask/Jinja2/Python/CSS Compiler
REM Uses Maven to generate ANTLR parsers and compile all Java code
REM ═══════════════════════════════════════════════════════════════════════════

echo.
echo ═══════════════════════════════════════════════════════════════════════════
echo  Flask/Jinja2/Python/CSS Compiler - Maven Build Script
echo ═══════════════════════════════════════════════════════════════════════════
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven not found in PATH
    echo Please install Maven from https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo [Step 1/2] Cleaning previous build...
call mvn clean
if errorlevel 1 (
    echo ERROR: Maven clean failed
    pause
    exit /b 1
)
echo.

echo [Step 2/2] Compiling project (ANTLR generation + Java compilation)...
call mvn compile
if errorlevel 1 (
    echo ERROR: Maven compile failed
    pause
    exit /b 1
)
echo.

echo ═══════════════════════════════════════════════════════════════════════════
echo  Build completed successfully!
echo ═══════════════════════════════════════════════════════════════════════════
echo.
echo What Maven did:
echo   [✓] Generated ANTLR lexers and parsers from grammar/ directory
echo   [✓] Compiled all Java source files from src/main/java/
echo   [✓] Created class files in target/ directory
echo.
echo Generated grammar files in: target/generated-sources/grammar/
echo Compiled classes in: target/classes/
echo.
echo To run the compiler, use: run.bat
echo.
pause
