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


if __name__ == '__main__':
    # todo 1) interfejs konsolowy do wyboru funkcji z listy
    # todo 2) rysowanie wykresu

    print("1) 2*x^3 + 4*x^2 + 2")
    print("2) sin(x)^3 + x")
    print("wprowadź numer funkcji, przy blednym numerze sie pozegnamy: ")
    co_powiedzial_idiota = input()
    wybrana_funkcja = 0
    if co_powiedzial_idiota == '1':
        wybrana_funkcja = first_option
    elif co_powiedzial_idiota == '2':
        wybrana_funkcja = second_option
    else:
        print("Do widzenia")
        sys.exit()

    przedzialik = Division(-5, 5)
    przedzialy_z_maksimami = calculate_unimodal_divisions(wybrana_funkcja, przedzialik, 30)

    for przedzial in przedzialy_z_maksimami:
        draw_division(wybrana_funkcja, przedzial)
        x = maximum_in_range_by_dychotomy_method(wybrana_funkcja, przedzial, 0, 10)
        plt.scatter(x, wybrana_funkcja(x))

    punkty = calculate_points(wybrana_funkcja, przedzialik, 10000)
    plt.plot(punkty[0], punkty[1])
    plt.show()


