# wartość funkcji w punkcie obliczana schematem Hornera
def horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


def points(function, begin, end, number):
    X = [0]*number
    Y = [0]*number
    step = (end-begin)/(number-1)
    current_point = begin
    for i in range(number):
        X[i] = current_point
        Y[i] = function(current_point)
        current_point += step
    return [X, Y]


def maximum_in_range(function, begin, end, accuracy):
    # todo
    return 0


# todo implementacja przykładowych funkcji do wyboru
# 2*x^3 + 4*x^2 + 2
def first_option(x):
    return horner_method(x, [2, 4, 0, 2])
