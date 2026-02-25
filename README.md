# Traffic Mitigation Smart Parking System

A comprehensive multi-module system for intelligent traffic management and smart parking solutions.

## 📁 Project Structure

This repository contains multiple independent modules:

### 🚗 Smart-Parking-Hub
- **Technology**: React + Node.js + TypeScript
- **Description**: Main parking management web application
- **Location**: `./Smart-Parking-Hub/`
- **Setup**: `cd Smart-Parking-Hub && npm install && npm run dev`

### 🤖 Traffic-Controller-AI
- **Technology**: React + Node.js + Three.js
- **Description**: AI-powered traffic control visualization
- **Location**: `./Traffic-Controller-AI/`
- **Setup**: `cd Traffic-Controller-AI && npm install && npm run dev`

### 📊 Traffic Analytics
- **Technology**: Python + YOLO + OpenCV
- **Description**: Computer vision-based traffic analysis system
- **Location**: `./traffic_analytics/`
- **Setup**: `cd traffic_analytics && pip install -r requirements.txt`

### 🛣️ Road Anomaly Detector
- **Technology**: Python/Pygame
- **Description**: Road surface anomaly detection system
- **Location**: `./app/RoadAnomalyDetector/`

## 🚀 Quick Start

### Prerequisites
- Node.js 16+
- Python 3.8+
- Git LFS (for large files)

### Install Git LFS
```bash
git lfs install
```

### Clone and Setup
```bash
git clone <repository-url>
cd traffic-mitigation-smart-parking-system

# Setup individual modules
cd Smart-Parking-Hub && npm install && cd ..
cd Traffic-Controller-AI && npm install && cd ..
cd traffic_analytics && pip install -r requirements.txt && cd ..
```

## 📦 Individual Module Downloads

Each module can be used independently:

1. **Smart-Parking-Hub only**: Download `Smart-Parking-Hub/` folder
2. **Traffic-Controller-AI only**: Download `Traffic-Controller-AI/` folder  
3. **Traffic Analytics only**: Download `traffic_analytics/` folder
4. **Road Anomaly Detector only**: Download `app/RoadAnomalyDetector/` folder

## 🗂️ File Organization

- `/docs/` - Documentation for all modules
- `/demo-assets/` - Shared demonstration assets
- Each module has its own `README.md` with detailed setup instructions

## 🤝 Contributing

Please refer to individual module README files for module-specific contribution guidelines.

## 📄 License

[Add your license information here]
