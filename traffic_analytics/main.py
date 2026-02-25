import cv2
import numpy as np
import time
from ultralytics import YOLO
from collections import deque

# ===============================
# CONFIG
# ===============================
VIDEO_SOURCE = "traffic.mp4"   # use 0 for webcam
FLOW_WINDOW = 60               # seconds (for veh/min)

# ===============================
# Globals
# ===============================
current_polygon = []
lanes = []                 # list of lane polygons
lane_counts = []           # set of vehicle IDs per lane
lane_flow_times = []       # timestamps per lane

# ===============================
# Mouse callback (draw lanes)
# ===============================
def draw_polygon(event, x, y, flags, param):
    global current_polygon, lanes, lane_counts, lane_flow_times

    if event == cv2.EVENT_LBUTTONDOWN:
        current_polygon.append((x, y))

    elif event == cv2.EVENT_RBUTTONDOWN:
        if len(current_polygon) >= 3:
            lanes.append(np.array(current_polygon, np.int32))
            lane_counts.append(set())
            lane_flow_times.append(deque())
            print(f"✅ Lane {len(lanes)} saved")
        current_polygon = []

# ===============================
# Load YOLOv8
# ===============================
model = YOLO("yolov8n.pt")

# ===============================
# Video source
# ===============================
cap = cv2.VideoCapture(VIDEO_SOURCE)

cv2.namedWindow("Traffic Analytics")
cv2.setMouseCallback("Traffic Analytics", draw_polygon)

# ===============================
# Main loop
# ===============================
while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        break

    # Run tracking
    results = model.track(frame, persist=True, conf=0.4)

    if results and results[0].boxes.id is not None:
        boxes = results[0].boxes.xyxy.cpu().numpy()
        ids = results[0].boxes.id.cpu().numpy()

        for box, track_id in zip(boxes, ids):
            x1, y1, x2, y2 = map(int, box)
            cx = int((x1 + x2) / 2)
            cy = int((y1 + y2) / 2)

            # Draw detection
            cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0), 2)
            cv2.circle(frame, (cx, cy), 4, (0, 255, 255), -1)
            cv2.putText(
                frame,
                f"ID {int(track_id)}",
                (x1, y1 - 6),
                cv2.FONT_HERSHEY_SIMPLEX,
                0.5,
                (0, 255, 0),
                2
            )

            # Check lane membership
            for i, lane in enumerate(lanes):
                inside = cv2.pointPolygonTest(lane, (cx, cy), False)
                if inside >= 0 and track_id not in lane_counts[i]:
                    lane_counts[i].add(track_id)
                    lane_flow_times[i].append(time.time())
                    print(f"🚗 Vehicle {track_id} counted in lane {i+1}")

    # ===============================
    # Draw lanes + flow rate
    # ===============================
    current_time = time.time()

    for i, lane in enumerate(lanes):
        # Remove old timestamps
        while lane_flow_times[i] and current_time - lane_flow_times[i][0] > FLOW_WINDOW:
            lane_flow_times[i].popleft()

        flow_rate = len(lane_flow_times[i]) * (60 / FLOW_WINDOW)

        cv2.polylines(frame, [lane], True, (255, 0, 0), 2)
        cv2.putText(
            frame,
            f"Lane {i+1}: {int(flow_rate)} veh/min",
            (lane[0][0], lane[0][1] - 10),
            cv2.FONT_HERSHEY_SIMPLEX,
            0.6,
            (255, 0, 0),
            2
        )

    # ===============================
    # Draw current polygon
    # ===============================
    for point in current_polygon:
        cv2.circle(frame, point, 4, (0, 0, 255), -1)

    cv2.imshow("Traffic Analytics", frame)

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break

# ===============================
# Cleanup
# ===============================
cap.release()
cv2.destroyAllWindows()
