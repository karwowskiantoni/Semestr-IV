import sys

import matplotlib.pyplot as plt
from Division import *
from NewtonCotesIntegral import *
from functions import *
# Twórcy: Antoni Karwowski, Michał Gebel


if __name__ == '__main__':
    print("1) 2*x^3 + 4*x^2 + 2")
    print("2) sin(x)^3 + x")
    print("3)  -3^x + 10x")
    print("4)  (sin(x) + 2)^2 + 5cos(x))")
    print("5)  7^sin(x))")
    print("6)  |x + 3| - 2")
    print("7)  |-20x^3 + 100x^2 + |8x| - 3|")

    print("Wprowadź numer funkcji, przy blednym numerze sie pozegnamy: ")
    answer = input()
    chosen_function = 0
    if answer == '1':
        chosen_function = first_option
    elif answer == '2':
        chosen_function = second_option
    elif answer == '3':
        chosen_function = third_option
    elif answer == '4':
        chosen_function = fourth_option
    elif answer == '5':
        chosen_function = fifth_option
    elif answer == '6':
        chosen_function = sixth_option
    elif answer == '7':
        chosen_function = seventh_option
    else:
        print("Polecenie było niejasne, do widzenia")
        sys.exit()

    calculate_integral(Division(-3, 7), chosen_function, calculate_newton_cotes_integral, 0.01)
    calculate_integral(Division(-3, 7), chosen_function, calculate_gauss_legendre_integral, 0.01)

    points = calculate_points(chosen_function, Division(-3, 7), 10000)
    plt.plot(points[0], points[1])
    plt.show()
