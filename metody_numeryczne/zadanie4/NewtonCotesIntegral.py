from functions import *
from math import sqrt


def calculate_integral(division, function, integral_calculation_function, iterations):
    current_integral = integral_calculation_function(function, division)
    iterations_number = 2
    while iterations_number < iterations:
        divisions = division.split_division(iterations_number)
        # last_integral = current_integral
        current_integral = sum([integral_calculation_function(function, x) for x in divisions])
        iterations_number += 1
        # if last_integral - current_integral < accuracy:
        #     break
    return current_integral


def calculate_newton_cotes_integral(function, division):
    nodes = calculate_points(function, division, 3)
    return ((nodes[0][2] - nodes[0][1]) / 3) * (nodes[1][0] + 4 * nodes[1][1] + nodes[1][2])


def calculate_gauss_legendre_integral(function, division):
    d_minus_c = (division.end_x - division.begin_x) / 2
    c_plus_d = (division.begin_x + division.end_x) / 2
    return d_minus_c * calculate_minus_one_one_integral(lambda x: function(d_minus_c * x + c_plus_d))


def calculate_minus_one_one_integral(chosen_function):
    # X = [-1/21*sqrt(245+14*sqrt(70)), -1/21*sqrt(245-14*sqrt(70)), 0, 1/21*sqrt(245-14*sqrt(70)), 1/21*sqrt(245+14*sqrt(70))]
    # A = [1/900*(322-13*sqrt(70)), 1/900*(322+13*sqrt(70)), 128/255 - jest zle, 1/900*(322+13*sqrt(70)), 1/900*(322-13*sqrt(70))]
    X = [-0.90618, -0.538469, 0, 0.538469, 0.90618]
    A = [0.236927, 0.478629, 0.568889, 0.478629, 0.236927]
    return sum([chosen_function(X[i]) * A[i] for i in range(len(X))])

