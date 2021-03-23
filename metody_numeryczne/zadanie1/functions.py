# zwraca wartość wielomianu w punkcie 'x' obliczoną schematem
# Hornera na podstawie współczynników wielomianu 'coefficients'
def calculate_by_horner_method(x, coefficients):
    value = coefficients[0]
    for i in range(1, len(coefficients)):
        value = value * x + coefficients[i]
    return value


# zwraca 'number_of_divisions' przedziałów na których funkcja 'function' może
# posiadać maksimum lokalne z przedziału 'division'
def calculate_unimodal_divisions(function, division, number_of_divisions):
    step = abs(division.end_x - division.begin_x) / number_of_divisions
    current_x = division.begin_x + step

    divisions_with_maximum = []

    for i in range(number_of_divisions):

        value_of_current_x = function(current_x)

        if value_of_current_x > function(current_x - step) and value_of_current_x > function(current_x + step):
            divisions_with_maximum.append(Division(current_x-step, current_x + step))

        current_x += step

    return divisions_with_maximum


# zwraca 'number' punktów funkcji 'function' z przedziału 'division'
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


def maximum_in_range_by_dychotomy_method(function, division, accuracy, iterations_number):
    proportion = 0.6
    current_division = division

    for i in range(iterations_number):
        left_x = current_division.begin_x * proportion + current_division.end_x * (1 - proportion)
        right_x = current_division.end_x * proportion + current_division.begin_x * (1 - proportion)
        value_of_left_x = function(left_x)
        value_of_right_x = function(right_x)

        if current_division.calculate_length() < accuracy:
            current_division.show()
            return current_division.calculate_middle_of_the_division()

        elif value_of_left_x > value_of_right_x:
            current_division = Division(current_division.begin_x, right_x)

        elif value_of_left_x < value_of_right_x:
            current_division = Division(left_x, current_division.end_x)

        else:
            current_division = Division(left_x, right_x)


    return current_division.calculate_middle_of_the_division()


def maximum_in_range_by_golden_division_method(function, division, accuracy, iterations_number):
    theta = 0.61803
    current_division = division
    left_x = (current_division.end_x - current_division.begin_x) * (-theta) + current_division.end_x
    right_x = (current_division.end_x - current_division.begin_x) * theta + current_division.begin_x
    value_of_left_x = function(left_x)
    value_of_right_x = function(right_x)

    for i in range(iterations_number):

        if current_division.calculate_length() < accuracy:
            return current_division.calculate_middle_of_the_division()

        elif value_of_left_x > value_of_right_x:
            current_division = Division(current_division.begin_x, right_x)

        elif value_of_left_x < value_of_right_x:
            current_division = Division(left_x, current_division.end_x)

        else:
            current_division = Division(left_x, right_x)

    return current_division.calculate_middle_of_the_division()


class Division:

    begin_x = 0
    end_x = 0

    def show(self):
        print("przedział < " + str(self.begin_x) + "; " + str(self.end_x) + " >")


    def calculate_length(self):
        return abs(self.end_x - self.begin_x)

    def calculate_middle_of_the_division(self):
        return self.begin_x + self.calculate_length() / 2

    def __init__(self, begin_x, end_x):
        self.begin_x = begin_x
        self.end_x = end_x
