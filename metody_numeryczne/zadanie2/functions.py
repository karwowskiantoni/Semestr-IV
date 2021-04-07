import utils


def try_swap_rows(matrix, k):
    for i in range(len(matrix) - k):
        if matrix[k][k] == 0:
            pom = matrix[k]
            matrix[k] = matrix[k + i]
            matrix[k + i] = pom
    return matrix


def interrupt_if_zero(value, message):
    if round(value, 10) == 0:
        print(message)
        exit()


def create_identity_matrix(matrix):
    current_matrix = matrix
    matrix_height = len(matrix)
    matrix_width = len(matrix[0])   # = matrix_height + 1
    number_of_eliminations = matrix_height
    for k in range(number_of_eliminations):
        try_swap_rows(current_matrix, k)
        interrupt_if_zero(current_matrix[k][k], "układ sprzeczny lub nieoznaczony")
        new_matrix = utils.create_matrix(matrix_height, matrix_width)
        for i in range(matrix_height):
            if i == k:
                for j in range(matrix_width):
                    new_matrix[k][j] = current_matrix[k][j] / current_matrix[k][k]
            else:
                for j in range(matrix_width):
                    new_matrix[i][j] = current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k]

        current_matrix = new_matrix
    utils.print_matrix(utils.round_matrix(current_matrix, 1))
