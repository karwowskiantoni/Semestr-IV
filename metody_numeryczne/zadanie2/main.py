def parse_matrix(filename):
    file = open(filename, "r")
    row = file.readline().split()
    print(row)


if __name__ == '__main__':
    # todo wczytywanie macierzy z pliku z pliku
    # x = [[3, 3, 1], [2, 5, 7], [1, 2, 1]]
    # y = [12, 33, 8]
    # x = [[3, 3, 1], [2, 5, 7], [-4, -10, -14]]
    # y = [1, 20, -20]
    # x = [[3, 2, 1, -1], [5, -1, 1, 2], [1, -1, 1, 2], [7, 8, 1, -7]]
    # y = [0, -4, 4, 6]
    # f.create_identity_matrix(x, y)
    parse_matrix("first_matrix")
