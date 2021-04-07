def print_matrix(array):
    for row in array:
        print(row)


def parse_matrix(filename):
    file = open(filename, "r")
    matrix = []
    row = [float(i) for i in file.readline().split()]
    while row:
        matrix.append(row)
        row = [float(i) for i in file.readline().split()]
    return matrix


def round_na_sterydach(value, digits):
    rounded_value = round(value, digits)
    if rounded_value == -0:
        return float(0)
    else:
        return rounded_value


def create_matrix(number_of_rows, number_of_columns):
    return [[float(0)] * number_of_columns for i in range(number_of_rows)]


def round_matrix(matrix, digits):
    if not isinstance(matrix, list):
        return round_na_sterydach(matrix, digits)
    if not isinstance(matrix[0], list):
        return [round_na_sterydach(matrix[i], digits) for i in range(len(matrix))]
    else:
        return [[round_na_sterydach(matrix[i][j], digits) for j in range(len(matrix[0]))] for i in range(len(matrix))]


