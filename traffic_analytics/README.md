# Traffic Analytics

A computer vision system for real-time traffic analysis using YOLOv8 object detection and tracking. This application detects vehicles, tracks them across frames, and analyzes traffic flow patterns including lane-based counting and flow rate calculations.

## Features

- **Real-time Vehicle Detection**: Uses YOLOv8 nano model for fast detection of cars, buses, trucks, and motorcycles
- **Multi-object Tracking**: Persistent tracking of vehicles across video frames
- **Lane-based Counting**: Interactive lane definition with polygon drawing
- **Flow Rate Analysis**: Calculates vehicles per minute for each lane
- **Visual Interface**: Real-time visualization with bounding boxes, track IDs, and flow statistics

## Project Structure

```
traffic_analytics/
├── main.py                 # Main application entry point
├── traffic.mp4            # Sample traffic video
├── yolov8n.pt            # YOLOv8 nano model weights
├── detection/            # Vehicle detection module
│   ├── __init__.py
│   └── yolo_detector.py  # YOLO-based detector
├── tracking/             # Object tracking module
│   ├── __init__.py
│   ├── track.py
│   ├── track_manager.py
│   ├── simple_tracker.py
│   └── bytetrack_wrapper.py
├── counting/             # Vehicle counting module
│   ├── __init__.py
│   ├── counter.py
│   ├── counting.py
│   ├── speed.py
│   └── queue.py
├── zones/                # Lane management module
│   ├── __init__.py
│   └── lane_manager.py   # Lane polygon management
├── ingestion/            # Video input module
│   ├── __init__.py
│   └── video_reader.py
├── aggregation/          # Data aggregation module
│   ├── __init__.py
│   └── aggregator.py
├── api/                  # API server module
│   ├── __init__.py
│   └── server.py
└── configs/              # Configuration module
    ├── __init__.py
```

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd traffic_analytics
```

2. Create and activate a virtual environment:
```bash
python -m venv venv
# On Windows:
venv\Scripts\activate
# On Unix/MacOS:
source venv/bin/activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

4. Download YOLOv8 model (if not included):
```python
from ultralytics import YOLO
model = YOLO('yolov8n.pt')  # This will download the model automatically
```

**Note**: If you encounter PyTorch `weights_only` errors, the requirements.txt includes compatible PyTorch versions (2.5.1) that avoid this issue.

## Usage

### Basic Usage

Run the main application with the included traffic video:
```bash
python main.py
```

### Using Webcam

To use your webcam instead of the video file, modify `main.py`:
```python
VIDEO_SOURCE = 0  # Change from "traffic.mp4" to 0
```

### Lane Definition

1. **Left Click**: Add points to define a lane polygon
2. **Right Click**: Complete and save the current lane
3. **Repeat**: Define multiple lanes as needed

The application will:
- Display detected vehicles with bounding boxes and track IDs
- Show lane boundaries in blue
- Display real-time flow rates (vehicles/minute) for each lane
- Print vehicle counting events to the console

### Controls

- **Left Click**: Add point to current lane polygon
- **Right Click**: Save current lane
- **'q' key**: Quit the application

## Configuration

Key parameters in `main.py`:

- `VIDEO_SOURCE`: Path to video file or camera index (0 for webcam)
- `FLOW_WINDOW`: Time window in seconds for flow rate calculation (default: 60 seconds)
- `conf`: Detection confidence threshold (default: 0.4)

## Dependencies

- **OpenCV**: Computer vision and image processing
- **Ultralytics**: YOLOv8 object detection
- **NumPy**: Numerical computations
- **Shapely**: Geometric operations for lane management

## Model Information

- **Model**: YOLOv8 nano (yolov8n.pt)
- **Classes**: car, bus, truck, motorcycle
- **Input Size**: Variable (processed by YOLOv8)
- **Confidence Threshold**: 0.4

## Performance

- **Real-time Processing**: Optimized for real-time video analysis
- **Memory Efficient**: Uses deque for timestamp management
- **Scalable**: Supports multiple lanes and simultaneous vehicle tracking

## Troubleshooting

1. **Model not found**: The YOLOv8 model will be downloaded automatically on first run
2. **Video not opening**: Check that the video file path is correct or webcam is accessible
3. **Low performance**: Consider reducing video resolution or using a more powerful GPU

## Future Enhancements

- [ ] Web-based interface for lane configuration
- [ ] Database integration for historical data
- [ ] REST API for remote access
- [ ] Speed estimation and violation detection
- [ ] Traffic light integration
- [ ] Cloud deployment support

## License

[Add your license information here]

## Contributing

[Add contribution guidelines here]
