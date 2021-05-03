class Division:

    begin_x = 0
    end_x = 0

    def show(self):
        print("przedział < " + str(self.begin_x) + "; " + str(self.end_x) + " >")

    def calculate_length(self):
        return abs(self.end_x - self.begin_x)

    def calculate_middle_of_the_division(self):
        return self.begin_x + self.calculate_length() / 2

    def __init__(self, begin_x, end_x):
        self.begin_x = begin_x
        self.end_x = end_x