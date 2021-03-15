import matplotlib.pyplot as plt
from functions import *

if __name__ == '__main__':
    # todo
    # 1) interfejs konsolowy do wyboru funkcji z listy
    # 2) rysowanie wykresu
    points = [[1, 2, 3], [2, 4, 6]]
    plt.plot(points[0], points[1])
    plt.show()
