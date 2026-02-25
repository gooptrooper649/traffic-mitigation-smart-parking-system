@echo off
REM Traffic Mitigation Smart Parking System - Setup Script (Windows)
REM This script helps users set up individual modules or the entire system

echo 🚦 Traffic Mitigation Smart Parking System Setup
echo ==============================================
echo.

REM Check if Git LFS is installed
git lfs version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Git LFS is not installed. Large files may not be handled properly.
    echo 📥 Install Git LFS: https://git-lfs.com/
    echo.
    set /p continue="Continue anyway? (y/N): "
    if /i not "%continue%"=="y" exit /b 1
) else (
    echo ✅ Git LFS is installed
    git lfs install
    echo.
)

:menu
echo What would you like to set up?
echo 1) Smart Parking Hub ^(React/Node.js^)
echo 2) Traffic Controller AI ^(React/Three.js^)
echo 3) Traffic Analytics ^(Python/YOLO^)
echo 4) Road Anomaly Detector ^(Python/Pygame^)
echo 5) All modules
echo 6) Exit
echo.

set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" goto setup_parking
if "%choice%"=="2" goto setup_traffic
if "%choice%"=="3" goto setup_analytics
if "%choice%"=="4" goto setup_detector
if "%choice%"=="5" goto setup_all
if "%choice%"=="6" goto exit
goto invalid_choice

:setup_parking
echo 📦 Setting up Smart Parking Hub...
echo 📍 Path: .\Smart-Parking-Hub
if not exist ".\Smart-Parking-Hub" (
    echo ❌ Module directory not found: .\Smart-Parking-Hub
    goto menu
)
cd .\Smart-Parking-Hub
echo 🔧 Running: npm install
npm install
if %errorlevel% equ 0 (
    echo ✅ Smart Parking Hub setup completed successfully!
) else (
    echo ❌ Smart Parking Hub setup failed!
    cd ..
    goto menu
)
cd ..
echo 🚀 To start: cd Smart-Parking-Hub && npm run dev
echo.
goto end

:setup_traffic
echo 📦 Setting up Traffic Controller AI...
echo 📍 Path: .\Traffic-Controller-AI
if not exist ".\Traffic-Controller-AI" (
    echo ❌ Module directory not found: .\Traffic-Controller-AI
    goto menu
)
cd .\Traffic-Controller-AI
echo 🔧 Running: npm install
npm install
if %errorlevel% equ 0 (
    echo ✅ Traffic Controller AI setup completed successfully!
) else (
    echo ❌ Traffic Controller AI setup failed!
    cd ..
    goto menu
)
cd ..
echo 🚀 To start: cd Traffic-Controller-AI && npm run dev
echo.
goto end

:setup_analytics
echo 📦 Setting up Traffic Analytics...
echo 📍 Path: .\traffic_analytics
if not exist ".\traffic_analytics" (
    echo ❌ Module directory not found: .\traffic_analytics
    goto menu
)
cd .\traffic_analytics
echo 🔧 Running: pip install -r requirements.txt
pip install -r requirements.txt
if %errorlevel% equ 0 (
    echo ✅ Traffic Analytics setup completed successfully!
) else (
    echo ❌ Traffic Analytics setup failed!
    cd ..
    goto menu
)
cd ..
echo 🚀 To start: cd traffic_analytics && python main.py
echo.
goto end

:setup_detector
echo 📦 Setting up Road Anomaly Detector...
echo 📍 Path: .\app\RoadAnomalyDetector
if not exist ".\app\RoadAnomalyDetector" (
    echo ❌ Module directory not found: .\app\RoadAnomalyDetector
    goto menu
)
cd .\app\RoadAnomalyDetector
echo 🔧 Running: pip install -r requirements.txt
pip install -r requirements.txt
if %errorlevel% equ 0 (
    echo ✅ Road Anomaly Detector setup completed successfully!
) else (
    echo ❌ Road Anomaly Detector setup failed!
    cd ..\..
    goto menu
)
cd ..\..
echo 🚀 Navigate to app\RoadAnomalyDetector and run the main script
echo.
goto end

:setup_all
echo 📦 Setting up all modules...
call :setup_parking_silent
call :setup_traffic_silent
call :setup_analytics_silent
call :setup_detector_silent
echo 🎉 All modules setup completed!
echo.
goto end

:setup_parking_silent
if exist ".\Smart-Parking-Hub" (
    cd .\Smart-Parking-Hub
    npm install >nul 2>&1
    cd ..
)
goto :eof

:setup_traffic_silent
if exist ".\Traffic-Controller-AI" (
    cd .\Traffic-Controller-AI
    npm install >nul 2>&1
    cd ..
)
goto :eof

:setup_analytics_silent
if exist ".\traffic_analytics" (
    cd .\traffic_analytics
    pip install -r requirements.txt >nul 2>&1
    cd ..
)
goto :eof

:setup_detector_silent
if exist ".\app\RoadAnomalyDetector" (
    cd .\app\RoadAnomalyDetector
    pip install -r requirements.txt >nul 2>&1
    cd ..\..
)
goto :eof

:invalid_choice
echo ❌ Invalid choice!
echo.
goto menu

:exit
echo 👋 Goodbye!
exit /b 0

:end
echo 📚 For detailed instructions, check individual module README files.
echo 🌐 Project documentation: .\docs\
echo.
pause
