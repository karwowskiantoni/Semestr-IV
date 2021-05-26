from numpy.ma import shape

from LinearEquationsCalculator import *
from Division import Division
from statistics import mean


def calculate_approximation_error(points, approximated_points):
    return mean([abs(points[1][i] - approximated_points[1][i]) for i in range(len(points[0]))])


def calculate_legendre_polynomial(degree, x):
    polynomial_k_minus_one = 1
    polynomial_k = x
    if degree == 0:
        return polynomial_k_minus_one
    elif degree == 1:
        return polynomial_k

    for k in range(1, degree):
        polynomial_k_plus_one = ((2 * k + 1) * x * polynomial_k - k * polynomial_k_minus_one) / (k + 1)
        polynomial_k_minus_one = polynomial_k
        polynomial_k = polynomial_k_plus_one

    return polynomial_k


class ApproximationFunctionCalculator:
    coefficients = []
    division = Division(0, 0)
    polynomials = []

    def __init__(self, nodes):
        self.division = Division(nodes[0][0], nodes[0][-1])
        size = len(nodes[0])
        matrix = [[0 for x in range(size + 1)] for y in range(size)]
        for i in range(size):
            for j in range(size + 1):
                matrix[i][j] = calculate_legendre_polynomial(j, self.convert_x_to_minus_one_one(nodes[0][i]))
            matrix[i][-1] = nodes[1][i]
        self.coefficients = gauss_jordan_solver(matrix)

    def approximated_function(self, x):
        result = 0
        for i in range(len(self.coefficients)):
            result += self.coefficients[i] * calculate_legendre_polynomial(i, self.convert_x_to_minus_one_one(x))
        return result

    def convert_x_to_minus_one_one(self, x):
        return (2 * x - self.division.begin_x - self.division.end_x) / (self.division.end_x - self.division.begin_x)
