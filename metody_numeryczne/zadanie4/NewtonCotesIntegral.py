from functions import *


def calculate_newton_cotes_integral(division, accuracy, function):
    current_integral = calculate_simpson_integral(calculate_points(function, division, 3))
    iterations_number = 2
    while True:
        divisions = division.split_division(iterations_number)
        last_integral = current_integral
        current_integral = sum([calculate_simpson_integral(calculate_points(function, x, 3)) for x in divisions])
        if current_integral - last_integral < accuracy:
            break
        iterations_number += 1
        print(iterations_number)
    return current_integral





def calculate_simpson_integral(nodes):
    return ((nodes[0][2] - nodes[0][1]) / 3) * (nodes[1][0] + 4 * nodes[1][1] + nodes[1][2])
