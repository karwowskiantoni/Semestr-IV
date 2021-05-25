# zwraca wartość wielomianu w punkcie 'x' obliczoną schematem
# Hornera na podstawie współczynników wielomianu 'coefficients'
import math


# zwraca 'number_of_points' równoodległych punktów funkcji 'function' z przedziału 'division'
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


def calculate_by_horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


def first_option(x):
    # 2*x^3 + 4*x^2 + 2
    return calculate_by_horner_method(x, [2, 4, 0, 2])


def second_option(x):
    # sin(x)^3 + x
    return calculate_by_horner_method(math.sin(x), [1, 0, 0, 0]) + calculate_by_horner_method(x, [1, 0])


def third_option(x):
    # -3^x + 10x
    return -pow(3, x) + calculate_by_horner_method(x, [10, 0])


def fourth_option(x):
    # (sin(x)+2)^2 + 5cos(x)
    return calculate_by_horner_method(math.sin(x)+2, [1, 0, 0]) + calculate_by_horner_method(math.cos(x), [5, 0])


def fifth_option(x):
    # 7^sin(x)
    return pow(7, math.sin(x))


def sixth_option(x):
    # |x + 3| - 2
    return abs(x+3) - 2


def seventh_option(x):
    # |-4x^3 + 100x^2 + |8x| - 3|
    return abs(calculate_by_horner_method(x, [-20, 100, 0, -3])) + calculate_by_horner_method(abs(x), [8, 0])



