from LinearEquationsCalculator import *

class ApproximationFunctionCalculator:
    coefficients = []

    def __init__(self, nodes):
        size = len(nodes[0])
        matrix = [[0 for x in range(size+1)]for y in range(size)]
        for i in range(size):
            for j in range(size+1):
                matrix[i][j] = self.calculate_legendre_polynomial(j, self.convert_x_to_minus_one_one(nodes[0][0], nodes[0][-1], nodes[0][i]))
            matrix[i][-1] = nodes[1][i]

        self.coefficients = gauss_jordan_solver(matrix)

    def calculate_legendre_polynomial(self, degree, x):
        if degree == 0:
            return 1
        elif degree == 1:
            return x

        polynomial_k_minus_one = 1
        polynomial_k = x

        for k in range(1, degree):
            polynomial_k_plus_one = ((2 * k + 1) * x * polynomial_k - k * polynomial_k_minus_one) / (k + 1)
            polynomial_k_minus_one = polynomial_k
            polynomial_k = polynomial_k_plus_one

        #lambda argument: (2 * k + 1) * x * polynomial_k - k * polynomial_k_minus_one
        return polynomial_k

    def multiply_coefficients_and_legendre_polynomial(self, begin, end, x):
        result = 0

        for i in range(len(self.coefficients)):
            result += self.coefficients[i] * self.calculate_legendre_polynomial(i, self.convert_x_to_minus_one_one(begin, end,x))

        return result

    def convert_x_to_minus_one_one(self, begin, end, x):
        return (2 * x - begin - end) / (end - begin)
