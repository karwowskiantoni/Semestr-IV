import time
import math

# zwraca wartość wielomianu w punkcie 'x' obliczoną schematem
# Hornera na podstawie współczynników wielomianu 'coefficients'
def calculate_by_horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


# zwraca 'number' punktów funkcji 'function' z przedziału 'division'
def calculate_points(function, division, number_of_points):
    X = [0] * number_of_points
    Y = [0] * number_of_points

    step = abs(division.end_x - division.begin_x) / (number_of_points - 1)
    current_x = division.begin_x
    for i in range(number_of_points):
        X[i] = current_x
        Y[i] = function(current_x)
        current_x += step
    return [X, Y]


def j(x_array, k, should_print):
    result = 1
    for i in range(len(x_array)):
        if k != i:
            if should_print: print("    (" + str(x_array[k]) + " - " + str(x_array[i]) + ")")
            result *= x_array[k] - x_array[i]
    return result


def calculate_coefficients_by_lagrange_interpolation(x_array, y_array):
    length = len(x_array)
    coefficients = [float(0)]*length
    for k in range(length):
        print(str(y_array[k]) + " / " + str(j(x_array, k, False)))
        coefficients[k] = y_array[k] / j(x_array, k, True)
    print(coefficients)

    return 0


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
