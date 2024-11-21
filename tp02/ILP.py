from pulp import LpProblem, LpMinimize, LpVariable, lpSum, LpBinary, value
import numpy as np

def read_graph_and_compute_shortest_paths(file_path):
    with open(file_path, "r") as file:
        lines = file.readlines()
        n, m, k = map(int, lines[0].strip().split())

        distances = np.full((n, n), float("inf"))
        np.fill_diagonal(distances, 0)  

        for line in lines[1:]:
            v, w, weight = map(int, line.strip().split())
            v -= 1  
            w -= 1  
            distances[v][w] = weight
            distances[w][v] = weight  

    for k_fw in range(n):
        for i in range(n):
            for j in range(n):
                if distances[i][k_fw] + distances[k_fw][j] < distances[i][j]:
                    distances[i][j] = distances[i][k_fw] + distances[k_fw][j]

    return n, k, distances

file_path = "pmed_instances/pmed1.txt"
n, k, distances = read_graph_and_compute_shortest_paths(file_path)

problem = LpProblem("k-Centers", LpMinimize)

y = [LpVariable(f"y_{i}", cat=LpBinary) for i in range(n)]
x = [[LpVariable(f"x_{i}_{j}", cat=LpBinary) for j in range(n)] for i in range(n)]
r = LpVariable("r", lowBound=0)

problem += r

for j in range(n):
    problem += lpSum(x[i][j] for i in range(n)) == 1

for i in range(n):
    for j in range(n):
        problem += x[i][j] <= y[i]

problem += lpSum(y) <= k

for i in range(n):
    for j in range(n):
        if np.isfinite(distances[i][j]):
            problem += distances[i][j] * x[i][j] <= r
        else:
            problem += x[i][j] == 0

problem.solve()

print("Raio máximo (r):", value(r))
for i in range(n):
    if value(y[i]) == 1:
        print(f"Centro: {i + 1}")  
for j in range(n):
    for i in range(n):
        if value(x[i][j]) == 1:
            print(f"Vértice {j + 1} associado ao centro {i + 1}")
