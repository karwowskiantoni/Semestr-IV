import sys

import matplotlib.pyplot as plt
from Division import *
from integralCalculations import *
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




    accuracy = [0.0000001 * (i+1) for i in range(1000)]
    newton_cotes_result = [calculate_integral(Division(-3, 7), chosen_function, calculate_newton_cotes_integral, acc) for acc in accuracy]
    gauss_legendre_result = [calculate_integral(Division(-3, 7), chosen_function, calculate_gauss_legendre_integral, acc) for acc in accuracy]
z
    plt.plot(accuracy, [i[1] for i in newton_cotes_result])
    plt.plot(accuracy, [i[1] for i in gauss_legendre_result])
    plt.title("iteracje dla przedziałów zwiększających się o 1")
    plt.xlabel("uzyskana dokładność [1]")
    plt.ylabel("liczba iteracji [1]")
    plt.show()
    input()
    plt.close()

    plt.scatter(accuracy, [i[2] for i in newton_cotes_result], s=1)
    plt.scatter(accuracy, [i[2] for i in gauss_legendre_result], s=1)
    plt.title("czas dla przedziałów zwiększających się o 1")
    plt.xlabel("uzyskana dokładność [1]")
    plt.ylabel("czas wykonania wszystkich iteracji [s]")
    plt.show()
    input()
    plt.close()





    iterations = [i for i in range(100)]
    newton_cotes_iteration_result = [calculate_integral_by_iterations(Division(-3, 7), chosen_function, calculate_newton_cotes_integral, iteration) for iteration in iterations]
    gauss_legendre_iteration_result = [calculate_integral_by_iterations(Division(-3, 7), chosen_function, calculate_gauss_legendre_integral, iteration) for iteration in iterations]


    plt.scatter([i[1] for i in newton_cotes_iteration_result], [i[2] for i in newton_cotes_iteration_result], s=1)
    plt.scatter([i[1] for i in gauss_legendre_iteration_result], [i[2] for i in gauss_legendre_iteration_result], s=1)
    plt.title("czas na iteracje dla przedzialow zwiekszajacych sie o 1")
    plt.xlabel("liczba iteracji [1]")
    plt.ylabel("czas wykonania wszystkich iteracji [s]")
    plt.show()
    input()
    plt.close()










    accuracy = [0.00000000001 * (i+1) for i in range(1000)]
    newton_cotes_second_result = [calculate_integral(Division(-3, 7), chosen_function, calculate_newton_cotes_integral, acc, False) for acc in accuracy]
    gauss_legendre_second_result = [calculate_integral(Division(-3, 7), chosen_function, calculate_gauss_legendre_integral, acc, False) for acc in accuracy]

    plt.title("iteracje dla przedziałów zwiększających się dwukrotnie")
    plt.xlabel("uzyskana dokładność [1]")
    plt.ylabel("liczba iteracji [1]")
    plt.plot(accuracy, [i[1] for i in newton_cotes_second_result])
    plt.plot(accuracy, [i[1] for i in gauss_legendre_second_result])
    plt.show()
    input()
    plt.close()

    plt.title("czas dla przedziałów zwiększających się dwukrotnie")
    plt.xlabel("uzyskana dokładność [1]")
    plt.ylabel("czas wszystkich iteracji [s]")
    plt.scatter(accuracy, [i[2] for i in newton_cotes_second_result], s=1)
    plt.scatter(accuracy, [i[2] for i in gauss_legendre_second_result], s=1)
    plt.show()
    input()
    plt.close()


    newton_cotes_iteration_result = [calculate_integral_by_iterations(Division(-3, 7), chosen_function, calculate_newton_cotes_integral, iteration, True) for iteration in iterations]
    gauss_legendre_iteration_result = [calculate_integral_by_iterations(Division(-3, 7), chosen_function, calculate_gauss_legendre_integral, iteration, True) for iteration in iterations]


    plt.title("czas na iteracje dla przedziałów zwiększających się dwukrotnie")
    plt.xlabel("liczba iteracji [1]")
    plt.ylabel("czas wykonania wszystkich iteracji [s]")
    plt.scatter([i[1] for i in newton_cotes_iteration_result], [i[2] for i in newton_cotes_iteration_result], s=1)
    plt.scatter([i[1] for i in gauss_legendre_iteration_result], [i[2] for i in gauss_legendre_iteration_result], s=1)
    plt.show()
    input()
    plt.close()
