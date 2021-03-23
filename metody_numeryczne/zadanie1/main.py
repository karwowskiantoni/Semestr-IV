import matplotlib.pyplot as plt
import math
import sys
from functions import *


def draw_division(function, division):
    plt.scatter(division.begin_x, function(division.begin_x))
    plt.scatter(division.end_x, function(division.end_x))


# todo implementacja przykładowych funkcji do wyboru

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
    # sin^2(x) + 5cos(x)
    return calculate_by_horner_method(math.sin(x), [1, 0, 0]) + calculate_by_horner_method(math.cos(x), [5, 0])


def fifth_option(x):
    # 7^sin(x)
    return pow(7, math.sin(x))


if __name__ == '__main__':
    print("1) 2*x^3 + 4*x^2 + 2")
    print("2) sin(x)^3 + x")
    print("3)  3^x + 10x")
    print("4)  sin^2(x) + 5cos(x))")
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

    przedzialik = Division(-5, 5)
    przedzialy_z_maksimami = calculate_unimodal_divisions(wybrana_funkcja, przedzialik, 30)

    for przedzial in przedzialy_z_maksimami:
        draw_division(wybrana_funkcja, przedzial)
        x = maximum_in_range_by_dychotomy_method(wybrana_funkcja, przedzial, 0.01, 10000000000000)
        plt.scatter(x, wybrana_funkcja(x))

    punkty = calculate_points(wybrana_funkcja, przedzialik, 10000)
    plt.plot(punkty[0], punkty[1])
    plt.show()


