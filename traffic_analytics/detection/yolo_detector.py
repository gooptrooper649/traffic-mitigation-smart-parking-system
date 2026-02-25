from ultralytics import YOLO

class YOLODetector:
    def __init__(self):
        self.model = YOLO("yolov8n.pt")  # nano = fast
        self.allowed = {"car", "bus", "truck", "motorcycle"}

    def detect(self, frame):
        results = self.model(frame, conf=0.4, verbose=False)
        detections = []

        for r in results:
            for box in r.boxes:
                cls = self.model.names[int(box.cls)]
                if cls in self.allowed:
                    x1, y1, x2, y2 = box.xyxy[0].tolist()
                    detections.append({
                        "bbox": [x1, y1, x2, y2],
                        "cls": cls
                    })
        return detections
