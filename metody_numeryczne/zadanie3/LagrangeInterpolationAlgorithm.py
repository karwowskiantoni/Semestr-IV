import math


class LagrangeInterpolationAlgorithm:
    coefficients = []
    x_array = []

    def j(self, k, x):
        return math.prod([x-self.x_array[i] if k != i else 1 for i in range(len(self.x_array))])

    def calculate_coefficients(self, x_array, y_array):
        self.x_array = x_array
        self.coefficients = [y_array[k] / self.j(k, x_array[k]) for k in range(len(x_array))]

    def calculate_value(self, x):
        return sum([self.coefficients[k] * self.j(k, x) for k in range(len(self.x_array))])
