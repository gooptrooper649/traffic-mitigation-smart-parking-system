import cv2
import time

class VideoReader:
    def __init__(self, source):
        self.cap = cv2.VideoCapture(source)

    def read(self):
        ret, frame = self.cap.read()
        if not ret:
            return None, None
        return frame, time.time()
