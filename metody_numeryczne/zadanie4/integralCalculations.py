from functions import *
from time import time


def calculate_integral(division, function, integral_calculation_function, accuracy, progress=True):
    timer = time()
    current_integral = integral_calculation_function(function, division)
    iterations_number = 2
    while True:
        if progress:
            divisions = division.split_division(iterations_number)
        else:
            divisions = division.split_division(pow(2, iterations_number-1))
        last_integral = current_integral
        current_integral = sum([integral_calculation_function(function, x) for x in divisions])
        iterations_number += 1
        if abs(last_integral - current_integral) < accuracy:
            print(accuracy)
            # print("liczba iteracji: " + str(iterations_number))
            # print("wartość całki oznaczonej z przedziału " + division.show() + " wynosi: " + str(current_integral))
            break
    return current_integral, iterations_number, time() - timer


def calculate_newton_cotes_integral(function, division):
    nodes = calculate_points(function, division, 3)
    return ((nodes[0][2] - nodes[0][1]) / 3) * (nodes[1][0] + 4 * nodes[1][1] + nodes[1][2])


def calculate_gauss_legendre_integral(function, division):
    d_minus_c = (division.end_x - division.begin_x) / 2
    c_plus_d = (division.begin_x + division.end_x) / 2
    return d_minus_c * calculate_minus_one_one_integral(lambda x: function(d_minus_c * x + c_plus_d))


def calculate_minus_one_one_integral(chosen_function):
    X = [-0.90618, -0.538469, 0, 0.538469, 0.90618]
    A = [0.236927, 0.478629, 0.568889, 0.478629, 0.236927]
    return sum([chosen_function(X[i]) * A[i] for i in range(len(X))])

