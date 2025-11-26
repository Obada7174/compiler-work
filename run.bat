@echo off
REM ═══════════════════════════════════════════════════════════════════════════
REM Run script for Flask/Jinja2/Python/CSS Compiler
REM ═══════════════════════════════════════════════════════════════════════════

setlocal enabledelayedexpansion

echo.
echo ═══════════════════════════════════════════════════════════════════════════
echo  Flask/Jinja2/Python/CSS Compiler - Run Script
echo ═══════════════════════════════════════════════════════════════════════════
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven not found in PATH
    echo Please install Maven from https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

REM Check if project is built
if not exist "target\classes\compiler\Main.class" (
    echo ERROR: Project not built yet!
    echo Please run build.bat first to compile the project.
    echo.
    pause
    exit /b 1
)

REM Display menu
echo Select which program to run:
echo.
echo   1. Default built-in example (no file needed)
echo   2. Test 1: Display Products (examples/test1_display_products.html)
echo   3. Test 2: Add Product Form (examples/test2_add_product.html)
echo   4. Test 3: Product Details (examples/test3_product_details.html)
echo   5. Custom file (you will be prompted for path)
echo.
set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" (
    echo.
    echo Running with default built-in example...
    echo.
    call mvn exec:java -Dexec.mainClass="compiler.Main"
) else if "%choice%"=="2" (
    echo.
    echo Running Test 1: Display Products...
    echo.
    call mvn exec:java -Dexec.mainClass="compiler.Main" -Dexec.args="examples/test1_display_products.html"
) else if "%choice%"=="3" (
    echo.
    echo Running Test 2: Add Product Form...
    echo.
    call mvn exec:java -Dexec.mainClass="compiler.Main" -Dexec.args="examples/test2_add_product.html"
) else if "%choice%"=="4" (
    echo.
    echo Running Test 3: Product Details...
    echo.
    call mvn exec:java -Dexec.mainClass="compiler.Main" -Dexec.args="examples/test3_product_details.html"
) else if "%choice%"=="5" (
    echo.
    set /p filepath="Enter the path to your template file: "
    echo.
    echo Running with custom file: !filepath!
    echo.
    call mvn exec:java -Dexec.mainClass="compiler.Main" -Dexec.args="!filepath!"
) else (
    echo.
    echo Invalid choice. Please run the script again and select 1-5.
    echo.
    pause
    exit /b 1
)

echo.
echo ═══════════════════════════════════════════════════════════════════════════
echo  Execution completed
echo ═══════════════════════════════════════════════════════════════════════════
echo.
pause
