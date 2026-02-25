import math

class SimpleTracker:
    def __init__(self, max_distance=50):
        self.next_id = 1
        self.tracks = {}
        self.max_distance = max_distance

    def _centroid(self, bbox):
        x1, y1, x2, y2 = bbox
        return int((x1+x2)/2), int((y1+y2)/2)

    def update(self, detections):
        updated_tracks = {}

        for det in detections:
            cx, cy = self._centroid(det["bbox"])
            matched_id = None

            for tid, track in self.tracks.items():
                tx, ty = track["centroid"]
                dist = math.hypot(cx - tx, cy - ty)
                if dist < self.max_distance:
                    matched_id = tid
                    break

            if matched_id is None:
                matched_id = self.next_id
                self.next_id += 1

            updated_tracks[matched_id] = {
                "id": matched_id,
                "bbox": det["bbox"],
                "cls": det["cls"],
                "centroid": (cx, cy)
            }

        self.tracks = updated_tracks
        return list(self.tracks.values())
