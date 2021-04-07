import utils


def create_identity_matrix(parameters_matrix):
    current_matrix = parameters_matrix
    # current_values = values
    matrix_size = len(parameters_matrix)
    number_of_eliminations = matrix_size

    for k in range(number_of_eliminations):
        # todo spytać czy da się rozróżnbić układ sprzeczny i nieoznaczony
        # todo oraz czy dzielenie przez zero jest jedynym takim warunkiem
        utils.interrupt_if_zero(current_matrix[k][k], "układ sprzeczny lub nieoznaczony")
        new_matrix = utils.create_matrix(matrix_size, matrix_size + 1)
        # new_values = create_matrix(matrix_size, 1)
        # todo spytać czy nie da się tego zrobić na macierzy niekwadratowej, nie oddzielając values
        for i in range(matrix_size):
            if i == k:
                # new_values[i] = current_values[k] / current_matrix[k][k]
                for j in range(matrix_size + 1):
                    new_matrix[k][j] = current_matrix[k][j] / current_matrix[k][k]
            else:
                # new_values[i] = current_values[i] - (current_values[k] * current_matrix[i][k]) / current_matrix[k][k]
                for j in range(matrix_size + 1):
                    new_matrix[i][j] = current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k]

        current_matrix = new_matrix
        # current_values = new_values

    utils.print_matrix(utils.round_matrix(current_matrix, 1))
    # print(round_matrix(current_values, 1))

