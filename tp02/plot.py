import os
import matplotlib.pyplot as plt
import numpy as np
from math import comb

def read_pmed_instances(directory, num_instances=40):
    vertices = []
    clusters = []
    instances = []
    
    for i in range(1, num_instances + 1):
        filename = f"pmed{i}.txt"
        filepath = os.path.join(directory, filename)
        if os.path.exists(filepath):
            with open(filepath, 'r') as file:
                line = file.readline().strip()
                try:
                    n, _, k = map(int, line.split())  
                    vertices.append(n)
                    clusters.append(k)
                    instances.append(i)
                except ValueError:
                    print(f"Erro ao processar {filename}: {line}")
    return instances, vertices, clusters

def plot_vertices_and_clusters(instances, vertices, clusters):
    x = np.arange(len(instances)) 
    width = 0.35  
    
    plt.figure(figsize=(14, 8))
    
    plt.bar(x - width / 2, vertices, width, label="Vértices", color="#1f77b4", alpha=0.9)
    plt.bar(x + width / 2, clusters, width, label="Clusters", color="#2ca02c", alpha=0.9)
    
    plt.title("Quantidade de Vértices e Clusters por Instância", fontsize=16, weight='bold')
    plt.xlabel("Instâncias", fontsize=14)
    plt.ylabel("Quantidade", fontsize=14)
    plt.xticks(x, [f"{i}" for i in instances], rotation=45, fontsize=10)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12, loc="upper left")
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    
    plt.tight_layout()
    plt.show()

def plot_combinations_growth(vertices, clusters):
    combinations = [comb(n, k) if k <= n else 0 for n, k in zip(vertices, clusters)]
    
    plt.figure(figsize=(14, 8))
    
    plt.plot(range(1, len(combinations) + 1), combinations, label=r"$C(n, k) = \frac{n!}{k!(n-k)!}$", marker='o', color="#d62728")
    
    plt.title("Crescimento do Número de Combinações", fontsize=16, weight='bold')
    plt.xlabel("Instâncias", fontsize=14)
    plt.ylabel("Número de Combinações", fontsize=14)
    plt.yscale("log")  
    plt.xticks(range(1, len(combinations) + 1), rotation=45, fontsize=10)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12, loc="upper left")
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    
    plt.tight_layout()
    plt.show()

    combinations = [comb(n, k) if k <= n else 0 for n, k in zip(vertices, clusters)]
    
    plt.figure(figsize=(14, 8))
    plt.plot(range(1, len(combinations) + 1), combinations, label="nCk", marker='o', color="#d62728")
    
    plt.title("Crescimento do Número de Combinações (nCk)", fontsize=16, weight='bold')
    plt.xlabel("Instâncias", fontsize=14)
    plt.ylabel("Número de Combinações (nCk)", fontsize=14)
    plt.yscale("log") 
    plt.xticks(range(1, len(combinations) + 1), rotation=45, fontsize=10)
    plt.yticks(fontsize=12)
    plt.legend(fontsize=12, loc="upper left")
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    
    plt.tight_layout()
    plt.show()

DIR = "./pmed_instances"

instances, vertices, clusters = read_pmed_instances(DIR)
plot_vertices_and_clusters(instances, vertices, clusters)
plot_combinations_growth(vertices, clusters)
