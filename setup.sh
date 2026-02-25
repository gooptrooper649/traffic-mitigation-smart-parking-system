#!/bin/bash

# Traffic Mitigation Smart Parking System - Setup Script
# This script helps users set up individual modules or the entire system

echo "🚦 Traffic Mitigation Smart Parking System Setup"
echo "=============================================="
echo ""

# Function to setup a module
setup_module() {
    local module_name=$1
    local module_path=$2
    local setup_command=$3
    
    echo "📦 Setting up $module_name..."
    echo "📍 Path: $module_path"
    
    if [ ! -d "$module_path" ]; then
        echo "❌ Module directory not found: $module_path"
        return 1
    fi
    
    cd "$module_path"
    
    echo "🔧 Running: $setup_command"
    eval $setup_command
    
    if [ $? -eq 0 ]; then
        echo "✅ $module_name setup completed successfully!"
    else
        echo "❌ $module_name setup failed!"
        return 1
    fi
    
    cd - > /dev/null
    echo ""
}

# Check if Git LFS is installed
check_git_lfs() {
    if ! command -v git-lfs &> /dev/null; then
        echo "⚠️  Git LFS is not installed. Large files may not be handled properly."
        echo "📥 Install Git LFS: https://git-lfs.com/"
        echo ""
        read -p "Continue anyway? (y/N): " -n 1 -r
        echo ""
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    else
        echo "✅ Git LFS is installed"
        git lfs install
        echo ""
    fi
}

# Main menu
echo "What would you like to set up?"
echo "1) Smart Parking Hub (React/Node.js)"
echo "2) Traffic Controller AI (React/Three.js)"
echo "3) Traffic Analytics (Python/YOLO)"
echo "4) Road Anomaly Detector (Python/Pygame)"
echo "5) All modules"
echo "6) Exit"
echo ""

read -p "Enter your choice (1-6): " choice

case $choice in
    1)
        check_git_lfs
        setup_module "Smart Parking Hub" "./Smart-Parking-Hub" "npm install"
        echo "🚀 To start: cd Smart-Parking-Hub && npm run dev"
        ;;
    2)
        check_git_lfs
        setup_module "Traffic Controller AI" "./Traffic-Controller-AI" "npm install"
        echo "🚀 To start: cd Traffic-Controller-AI && npm run dev"
        ;;
    3)
        check_git_lfs
        setup_module "Traffic Analytics" "./traffic_analytics" "pip install -r requirements.txt"
        echo "🚀 To start: cd traffic_analytics && python main.py"
        ;;
    4)
        setup_module "Road Anomaly Detector" "./app/RoadAnomalyDetector" "pip install -r requirements.txt"
        echo "🚀 Navigate to app/RoadAnomalyDetector and run the main script"
        ;;
    5)
        check_git_lfs
        setup_module "Smart Parking Hub" "./Smart-Parking-Hub" "npm install"
        setup_module "Traffic Controller AI" "./Traffic-Controller-AI" "npm install"
        setup_module "Traffic Analytics" "./traffic_analytics" "pip install -r requirements.txt"
        setup_module "Road Anomaly Detector" "./app/RoadAnomalyDetector" "pip install -r requirements.txt"
        echo "🎉 All modules setup completed!"
        ;;
    6)
        echo "👋 Goodbye!"
        exit 0
        ;;
    *)
        echo "❌ Invalid choice!"
        exit 1
        ;;
esac

echo ""
echo "📚 For detailed instructions, check individual module README files."
echo "🌐 Project documentation: ./docs/"
