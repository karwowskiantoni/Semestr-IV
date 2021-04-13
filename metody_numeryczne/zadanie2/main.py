import functions
import utils

if __name__ == '__main__':
    print("Wprowadź numer macierzy którą chcesz wprowadzić: ")
    answer = input()
    if answer == '1':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/first_matrix"))
    elif answer == '2':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/second_matrix"))
    elif answer == '3':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/third_matrix"))
    elif answer == '4':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/fourth_matrix"))
    elif answer == '5':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/fifth_matrix"))
    elif answer == '6':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/sixth_matrix"))
    elif answer == '7':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/seventh_matrix"))
    elif answer == '8':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/eighth_matrix"))
    elif answer == '9':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/ninth_matrix"))
    elif answer == '10':
        functions.gauss_jordan_solver(utils.parse_matrix("matrices/tenth_matrix"))
    else:
        print("nie ma takiego numeru")
        quit(0)
