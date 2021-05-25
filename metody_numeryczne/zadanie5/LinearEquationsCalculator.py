def gauss_jordan_solver(matrix):
    current_matrix = matrix
    matrix_height = len(matrix)
    matrix_width = len(matrix[0])

    for k in range(matrix_height):
        for i in range(len(current_matrix) - k):
            if current_matrix[k][k] == 0:
                current_matrix[k], current_matrix[k + 1] = current_matrix[k + 1], current_matrix[k]
        new_matrix = [[float(0)] * matrix_width for i in range(matrix_height)]
        for i in range(matrix_height):
            if i == k:
                for j in range(matrix_width):
                    new_matrix[k][j] = current_matrix[k][j] / current_matrix[k][k]
            else:
                for j in range(matrix_width):
                    new_matrix[i][j] = current_matrix[i][j] - (current_matrix[k][j] * current_matrix[i][k]) / current_matrix[k][k]
        current_matrix = new_matrix

    return [row[-1] for row in current_matrix]