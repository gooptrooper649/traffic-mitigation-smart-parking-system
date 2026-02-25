class Counter:
    def __init__(self):
        self.counted_ids = set()
        self.count = 0

    def update(self, tracks):
        for t in tracks:
            if t["id"] not in self.counted_ids:
                self.count += 1
                self.counted_ids.add(t["id"])

    def get_count(self):
        return self.count
