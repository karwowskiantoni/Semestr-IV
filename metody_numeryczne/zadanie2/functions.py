import utils


def try_to_swap_rows(matrix, k):
    for i in range(len(matrix) - k):
        if matrix[k][k] == 0:
            pom = matrix[k]
            matrix[k] = matrix[k + i]
            matrix[k + i] = pom
    return matrix


def gauss_jordan_solver(matrix):
    is_undefined = False
    current_matrix = matrix
    matrix_height = len(matrix)
    matrix_width = len(matrix[0])   # = matrix_height + 1
    number_of_eliminations = matrix_height
    for k in range(number_of_eliminations):
        current_matrix = try_to_swap_rows(current_matrix, k)
        if round(current_matrix[k][k], 10) == 0:
            if current_matrix[k][matrix_width-1] != 0:
                utils.print_matrix(utils.round_matrix(current_matrix, 1))
                print("uklad jest sprzeczny")
                return
            else:
                is_undefined = True
                continue
        new_matrix = utils.create_matrix(matrix_height, matrix_width)
        for i in range(matrix_height):
            if i == k:
                for j in range(matrix_width):
                    new_matrix[k][j] = current_matrix[k][j] / current_matrix[k][k]
            else:
                for j in range(matrix_width):
                    new_matrix[i][j] = current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k]

        current_matrix = new_matrix

    if is_undefined:
        utils.print_matrix(utils.round_matrix(current_matrix, 1))
        print("układ jest nieoznaczony")
    else:
        utils.print_matrix(utils.round_matrix(current_matrix, 1))
        print("układ posiada jedno rozwiązanie w postaci wektora rozwiązań (ostatnia kolumna)")
