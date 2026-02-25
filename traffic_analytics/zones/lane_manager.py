from shapely.geometry import Point, Polygon

class LaneManager:
    def __init__(self, lane_defs):
        self.lanes = lane_defs

    def assign_lane(self, cx, cy):
        p = Point(cx, cy)
        for lane_id, poly in self.lanes.items():
            if poly.contains(p):
                return lane_id
        return None
