# zwraca wartość wielomianu w punkcie 'x' obliczoną schematem
# Hornera na podstawie współczynników wielomianu 'coefficients'
def horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


# zwraca przedziały na których funkcja 'function' może posiadać minimum
# i maksimum lokalne z przedziału <'begin', 'end'> z dokładnością 'step'
def unimodal_division(function, begin_x, end_x, step):
    current_point = begin_x + step

    divisions_with_maximum = []
    divisions_with_minimum = []

    iterations = int((end_x-begin_x)/step) - 2
    for i in range(iterations):

        if function(current_point) < function(current_point - step) and function(current_point) < function(current_point + step):
            divisions_with_minimum.append(current_point)
            print("minimum")

        if function(current_point) > function(current_point - step) and function(current_point) > function(current_point + step):
            divisions_with_maximum.append(current_point)
            print("maksimum")

        current_point += step

    return [divisions_with_minimum, divisions_with_maximum]


# zwraca 'number' punktów funkcji 'function' z przedziału <'begin'; 'end'>
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
    # todo po co to komu
    return 0


