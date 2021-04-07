def print_matrix(array):
    for row in array:
        print(row)


def round_na_sterydach(value, digits):
    rounded_value = round(value, digits)
    if rounded_value == -0:
        return float(0)
    else:
        return rounded_value


def create_matrix(number_of_rows, number_of_columns):
    return [[float(0)] * number_of_columns for i in range(number_of_rows)]


def interrupt_if_zero(value, message):
    if value == 0:
        print(message)
        exit()


def round_matrix(matrix, digits):
    # todo spytać czy zaokrąglanie tylko w ostatnim etapie
    if not isinstance(matrix, list):
        return round_na_sterydach(matrix, digits)
    if not isinstance(matrix[0], list):
        return [round_na_sterydach(matrix[i], digits) for i in range(len(matrix))]
    else:
        return [[round_na_sterydach(matrix[i][j], digits) for j in range(len(matrix[0]))] for i in range(len(matrix))]


def create_identity_matrix(parameters_matrix, values):
    current_matrix = parameters_matrix
    current_values = values
    matrix_size = len(parameters_matrix)
    number_of_eliminations = matrix_size

    for k in range(number_of_eliminations):
        # todo spytać czy da się rozróżnbić układ sprzeczny i nieoznaczony
        # todo oraz czy dzielenie przez zero jest jedynym takim warunkiem
        interrupt_if_zero(current_matrix[k][k], "układ sprzeczny lub nieoznaczony")
        new_matrix = create_matrix(matrix_size, matrix_size)
        new_values = create_matrix(matrix_size, 1)
        # todo spytać czy nie da się tego zrobić na macierzy niekwadratowej, nie oddzielając values
        for i in range(matrix_size):
            if i == k:
                new_values[i] = current_values[k] / current_matrix[k][k]
                for j in range(matrix_size):
                    new_matrix[k][j] = current_matrix[k][j] / current_matrix[k][k]
            else:
                new_values[i] = current_values[i] - (current_values[k] * current_matrix[i][k]) / current_matrix[k][k]
                for j in range(matrix_size):
                    new_matrix[i][j] = current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k]

        current_matrix = new_matrix
        current_values = new_values

    print_matrix(round_matrix(current_matrix, 1))
    print(round_matrix(current_values, 1))

