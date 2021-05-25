class Division:

    begin_x = 0
    end_x = 0

    def show(self):
        return "[" + str(self.begin_x) + "; " + str(self.end_x) + "]"

    def calculate_length(self):
        return abs(self.end_x - self.begin_x)

    def split_division(self, number):
        divisions = []
        last_end = self.begin_x
        step = self.calculate_length()/number
        for x in range(number):
            divisions.append(Division(last_end, last_end + step))
            last_end += step
        return divisions

    def calculate_middle_of_the_division(self):
        return self.begin_x + self.calculate_length() / 2

    def __init__(self, begin_x, end_x):
        self.begin_x = begin_x
        self.end_x = end_x