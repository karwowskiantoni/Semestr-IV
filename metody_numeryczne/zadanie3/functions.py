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



