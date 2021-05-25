import sys

import matplotlib.pyplot as plt
from Division import *
from ApproximationFunctionCalculator import *
from functions import *
# Twórcy: Antoni Karwowski, Michał Gebel


if __name__ == '__main__':
    print("1) sin(x)^3 + x")
    print("2)  -3^x + 10x")
    print("3)  (sin(x) + 2)^2 + 5cos(x))")
    print("4)  7^sin(x))")

    print("Wprowadź numer funkcji, przy blednym numerze sie pozegnamy: ")
    answer = input()
    chosen_function = 0
    if answer == '1':
        chosen_function = second_option
    elif answer == '2':
        chosen_function = third_option
    elif answer == '3':
        chosen_function = fourth_option
    elif answer == '4':
        chosen_function = fifth_option
    else:
        print("Polecenie było niejasne, do widzenia")
        sys.exit()

    points = calculate_points(chosen_function, Division(-10, 3), 1000)
    plt.plot(points[0], points[1])

    calculator = ApproximationFunctionCalculator(calculate_points(chosen_function, Division(-10,3), 50))

    approximated_points = calculate_points(lambda x: calculator.multiply_coefficients_and_legendre_polynomial(-10, 3, x), Division(-100,100), 1000)
    plt.plot(approximated_points[0], approximated_points[1])

    plt.show()


