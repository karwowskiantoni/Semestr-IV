import sys

import matplotlib.pyplot as plt
from Division import *
from ApproximationFunctionCalculator import *
from functions import *
# Twórcy: Antoni Karwowski, Michał Gebel


if __name__ == '__main__':

    while True:
        # print("1) sin(x)^3 + x")
        # print("2)  -3^x + 10x")
        # print("3)  (sin(x) + 2)^2 + 5cos(x))")
        # print("4)  7^sin(x))")
        # print("5)  |x + 3| - 2")
        # print("Wprowadź numer funkcji, przy blednym numerze sie pozegnamy: ")
        # answer = input()
        # chosen_function = 0
        # if answer == '1':
        #     chosen_function = second_option
        # elif answer == '2':
        #     chosen_function = third_option
        # elif answer == '3':
        #     chosen_function = fourth_option
        # elif answer == '4':
        #     chosen_function = fifth_option
        # elif answer == '5':
        #     chosen_function = sixth_option
        #
        # else:
        #     print("Polecenie było niejasne, do widzenia")
        #     sys.exit()

        print("Wprowadź zadaną dokładność: ")
        accuracy = float(input())
        division = Division(-10, 3)


        polynomial_numbers = []
        node_number = 0
        for i in range(3, 15):
            print(f'liczę sobie wielomian stopnia {i}')

            coefficients = random_polynomial_coefficients(i)
            chosen_function = lambda x: calculate_by_horner_method(x, coefficients)

            calculator = ApproximationFunctionCalculator(calculate_points(chosen_function, division, 2))
            points = calculate_points(chosen_function, division, 1000)
            approximated_points = calculate_points(calculator.approximated_function, division, 1000)
            error = calculate_approximation_error(points, approximated_points)


            node_number = 3

            while node_number  < i+ 3:
                calculator = ApproximationFunctionCalculator(calculate_points(chosen_function, division, node_number))
                points = calculate_points(chosen_function, division, 1000)
                approximated_points = calculate_points(calculator.approximated_function, division, 1000)
                error = calculate_approximation_error(points, approximated_points)
                node_number += 1
                print(f'liczba węzłów: {node_number}, średni błąd aproksymacji: {error}')
        # plt.plot(polynomial_numbers, errors_minus_one)
        # plt.show()



