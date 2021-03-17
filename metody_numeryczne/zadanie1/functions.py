# zwraca wartość wielomianu w punkcie 'x' obliczoną schematem
# Hornera na podstawie współczynników wielomianu 'coefficients'
def calculate_by_horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


def calculate_middle_of_the_division(division):
    return division.begin_x + abs(division.end_x - division.begin_x)/2


# zwraca 'number_of_divisions' przedziałów na których funkcja 'function' może
# posiadać minimum lub maksimum lokalne z przedziału 'division'
def calculate_unimodal_divisions(function, division, number_of_divisions):
    step = abs(division.end_x - division.begin_x) / number_of_divisions
    current_x = division.begin_x + step

    divisions_with_maximum = []
    divisions_with_minimum = []

    for i in range(number_of_divisions):

        value_of_current_x = function(current_x)
        value_of_current_x_minus_step = function(current_x - step)
        value_of_current_x_plus_step = function(current_x + step)

        if value_of_current_x < value_of_current_x_minus_step\
                and value_of_current_x < value_of_current_x_plus_step:
            divisions_with_minimum.append(Division(current_x-step, current_x + step))

        if function(current_x) > value_of_current_x_minus_step\
                and value_of_current_x > value_of_current_x_plus_step:
            divisions_with_maximum.append(Division(current_x-step, current_x + step))

        current_x += step

    return [divisions_with_minimum, divisions_with_maximum]


# zwraca 'number' punktów funkcji 'function' z przedziału division
def calculate_points(function, division, number_of_points):
    X = [0] * number_of_points
    Y = [0] * number_of_points

    step = abs(division.end_x - division.begin_x) / (number_of_points - 1)
    current_x = division.begin_x
    for i in range(number_of_points):
        X[i] = current_x
        Y[i] = function(current_x)
        current_x += step
    return [X, Y]


def maximum_in_range(function, begin, end, accuracy):
    # todo po co to komu
    return 0

def maximum_in_range_by_golden_division_method(function, division, accuracy, iterations_number):
    # todo
    return 0

class Division:
    begin_x = 0
    end_x = 0

    def __init__(self, begin_x, end_x):
        self.begin_x = begin_x
        self.end_x = end_x
