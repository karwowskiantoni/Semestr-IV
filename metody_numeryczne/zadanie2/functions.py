def print_matrix(array):
    for row in array:
        print(row)


def create_matrix(number_of_rows, number_of_columns):
    return [[float(0)] * number_of_columns for i in range(number_of_rows)]


def interrupt_if_zero(value, message):
    if value == 0:
        print(message)
        exit()


def create_identity_matrix(matrix, values):
    current_matrix = matrix
    current_values = values
    matrix_size = len(matrix)
    number_of_eliminations = matrix_size

    for k in range(number_of_eliminations):
        interrupt_if_zero(current_matrix[k][k], "układ sprzeczny lub nieoznaczony")
        new_matrix = create_matrix(matrix_size, matrix_size)
        new_values = [float(0)]*matrix_size
        for i in range(matrix_size):
            if i == k:
                new_values[i] = round(current_values[k] / current_matrix[k][k], 3)
                for j in range(matrix_size):
                    new_matrix[k][j] = round(current_matrix[k][j] / current_matrix[k][k], 3)
            else:
                new_values[i] = round(current_values[i] - (current_values[k] * current_matrix[i][k]) / current_matrix[k][k], 3)
                for j in range(matrix_size):
                    new_matrix[i][j] = round(current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k], 3)

        current_matrix = new_matrix
        current_values = new_values
    print_matrix(current_matrix)
    print(current_values)

