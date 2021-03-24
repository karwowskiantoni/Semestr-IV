import matplotlib.pyplot as plt
import math
import sys
from functions import *


def draw_division(function, division):
    plt.scatter(division.begin_x, function(division.begin_x))
    plt.scatter(division.end_x, function(division.end_x))


def first_option(x):
    # 2*x^3 + 4*x^2 + 2
    return calculate_by_horner_method(x, [2, 4, 0, 2])


def second_option(x):
    # sin(x)^3 + x
    return calculate_by_horner_method(math.sin(x), [1, 0, 0, 0]) + calculate_by_horner_method(x, [1, 0])


def third_option(x):
    # 3^x + 10x
    return -pow(3, x) + calculate_by_horner_method(x, [10, 0])


def fourth_option(x):
    # (sin(x)+2)^2 + 5cos(x)
    return calculate_by_horner_method(math.sin(x)+2, [1, 0, 0]) + calculate_by_horner_method(math.cos(x), [5, 0])


def fifth_option(x):
    # 7^sin(x)
    return pow(7, math.sin(x))


if __name__ == '__main__':
    print("1) 2*x^3 + 4*x^2 + 2")
    print("2) sin(x)^3 + x")
    print("3)  3^x + 10x")
    print("4)  (sin(x) + 2)^2 + 5cos(x))")
    print("5)  7^sin(x))")

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
    else:
        print("Polecenie było niejasne, do widzenia")
        sys.exit()

    print("Wprowadź początek badanego przedziału: ")
    begin = float(input())
    print("Wprowadź koniec badanego przedziału: ")
    end = float(input())
    division = Division(begin, end)
    print("wprowadź liczbę przedziałów : ")
    step = int(input())

    print("Którą metodą chcesz znaleźć maksimum funkcji")
    print("1) Metoda dychotomii")
    print("2) Metoda złotego podziału")

    method = input()
    if method != '1' and method != '2':
        print("Polecenie było niejasne, do widzenia")
        sys.exit()

    print("Wprowadź kryterium stopu: ")
    print("1) Liczba iteracji")
    print("2) Dokładność")
    condition = input()

    if condition == '1':
        print("Wprowadź liczbę iteracji: ")
        iteration_number = int(input())
        accuracy = 0
    elif condition == '2':
        print("Wprowadź dokładność: ")
        accuracy = float(input())
        iteration_number = 9999999999999
    else:
        print("Polecenie było niejasne, do widzenia")
        sys.exit()

    divisions_with_maximums = calculate_unimodal_divisions(chosen_function, division, step)

    if method == '1':
        x = maximum_in_range_by_dychotomy_method(chosen_function, divisions_with_maximums[0], accuracy, iteration_number)

    elif method == '2':
        x = maximum_in_range_by_golden_division_method(chosen_function, divisions_with_maximums[0], accuracy, iteration_number)

    plt.scatter(x, chosen_function(x))

    punkty = calculate_points(chosen_function, division, 10000)
    plt.plot(punkty[0], punkty[1])
    plt.show()


