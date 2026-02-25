# 🚦 Traffic Mitigation & Smart Parking Management System

A comprehensive multi-module system for intelligent traffic management and smart parking solutions.

## 📌 Overview

A modular intelligent urban traffic management prototype designed to reduce congestion, optimize routing, improve parking efficiency, and enable citizen-driven road quality monitoring.

This system integrates:

- Smart routing
- Adaptive traffic light simulation
- Event-aware rerouting
- Illegal parking detection (decision-support)
- Parking management
- Traffic density analytics
- Citizen pothole reporting (Android app)

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

## 🏗 System Architecture

High-level modular design:

```
Frontend (React + Map)
├── Smart Routing Engine
├── Traffic Signal Controller
├── Event Rerouting Engine
├── Parking Management System
├── Enforcement Support Module
└── Data Analytics Layer
        │
Backend (Node / Python)
        │
Storage (GeoDB + JSON APIs)
```

## 📱 Citizen Road Quality Monitoring Module (Android)

**Features:**
- Automatic pothole detection using accelerometer
- GPS-based geo-tagging
- Manual reporting with image selection
- Realtime database logging
- Severity marking on map
- Multi-device confirmation logic

*Note: Cloud image storage requires backend billing configuration. For prototype phase, metadata storage is enabled.*

## 🚗 1. Smart Junction Avoidance

- Predefined congestion zones (GeoJSON)
- Penalty-based routing
- A* / Dijkstra simulation
- Visual comparison of routes

## 🚦 2. Adaptive Traffic Light System

- Dynamic priority calculation
- Aging-based fairness
- Simulated density control
- Real-time signal UI updates

## 🎉 3. Event-Aware Rerouting

- Manual or scheduled event injection
- Dynamic congestion penalty adjustment
- Auto route recalculation
- Alert visualization

## 🅿️ 4. Integrated Parking System

- Multi-floor parking simulation
- Live slot status
- Location-based navigation
- Occupancy impact on routing engine

## 🚨 5. Enforcement Decision Support

- No-parking zone mapping
- Image-based violation reporting (prototype)
- Geo-tag + timestamp logging
- Authority notification simulation

## 🛣 6. Traffic Density Analytics

- Area-based vehicle density logging
- Time-based trend simulation
- Heatmap generation
- Research-oriented dataset generation

## 🕳 7. Pothole & Road Damage Detection

- Citizen submission
- Auto detection via sensors
- Geo-tagging
- Status lifecycle: Reported → Acknowledged → Fixed → Verified

## 🛠 Tech Stack

**Frontend:**
- React
- Mapbox / Leaflet
- Tailwind / MUI

**Backend:**
- Node.js / FastAPI
- PostgreSQL + PostGIS
- Firebase (prototype stage)

**Mobile:**
- Kotlin (Android)
- Google Maps SDK
- Firebase Realtime Database

## 📌 Development Phases

**Phase 1 – Prototype**
Simulated traffic, static data, manual events.

**Phase 2 – Pilot**
Live routing integration, expanded dataset.

**Phase 3 – Deployment**
ML-based detection, IoT integration, civic dashboards.

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
git clone https://github.com/gooptrooper649/traffic-mitigation-smart-parking-system.git
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
