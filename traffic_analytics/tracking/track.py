class Track:
    def __init__(self, track_id, cls):
        self.id = track_id
        self.cls = cls
        self.centroids = []      # [(x, y, ts)]
        self.lane_id = None
        self.direction = None
        self.counted = False
