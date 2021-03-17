import matplotlib.pyplot as plt
import math
from functions import *

# todo implementacja przykładowych funkcji do wyboru

def first_option(x):
    # 2*x^3 + 4*x^2 + 2
    return horner_method(x, [2, 4, 0, 2])


def second_option(x):
    # sin(x) + 5x^2
    return horner_method(math.sin(x), [1, 0, 0, 0]) + horner_method(x, [0, 0.001, 0])


if __name__ == '__main__':
    # todo 1) interfejs konsolowy do wyboru funkcji z listy
    # todo 2) rysowanie wykresu

    wybrana_funkcja = second_option
    punkty = points(wybrana_funkcja, -100, 100, 10000)
    ekstrema = unimodal_division(wybrana_funkcja, -100, 100, 0.001)

    plt.scatter(ekstrema[1][0], second_option(ekstrema[1][0]))
    plt.plot(punkty[0], punkty[1])
    plt.show()
