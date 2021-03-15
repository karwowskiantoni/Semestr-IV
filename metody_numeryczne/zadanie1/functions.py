# wartość funkcji w punkcie obliczana schematem Hornera
def horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


def points(function, begin, end, number):
    # todo
    return function()


def maximum_in_range(function, begin, end, accuracy):
    # todo
    return 0






# todo implementacja przykładowych funkcji do wyboru
# 2*x^3 + 4*x^2 + 2
def first_option(x):
    return horner_method(x, [2, 4, 0, 2])
