import networkx as nx
import random
import argparse

def gerar_grafo(num_vertices, file_path, file_path_resp):
    g = nx.Graph()
    
    g.add_nodes_from(range(1, num_vertices + 1))

    # Adicionar as arestas necessárias para o grafo ser conexo (n - 1)
    for i in range(1, num_vertices):
        g.add_edge(i, i + 1)

    num_edges_to_add = int(num_vertices * 0.2)  # Gerar uma quantidade aleatória de arestas para serem adicionadas

    # Adicionar arestas extras para criar múltiplos componentes biconexos
    while num_edges_to_add > 0:
        v1 = random.randint(1, num_vertices)
        v2 = random.randint(1, num_vertices)
        
        if v1 != v2 and not g.has_edge(v1, v2):
            g.add_edge(v1, v2)
            num_edges_to_add -= 1

    # Obter as arestas do grafo
    edges = list(g.edges())
    
    with open(file_path, 'w') as file:
        file.write(f"{num_vertices} {len(edges)}\n")
        
        for edge in edges:
            file.write(f"{edge[0]} {edge[1]}\n")

    # Obter componentes biconexos, pontes e articulações
    blocks = list(nx.biconnected_components(g))
    articulations = list(nx.articulation_points(g))
    bridges = list(nx.bridges(g))

    with open(file_path_resp, 'w') as file:
        for block in blocks:
            file.write(f"[{', '.join(map(str, block))}]\n")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Gera um grafo com número de vértices especificado e salva em arquivos.')
    parser.add_argument('num_vertices', type=int, help='Número de vértices do grafo')
    parser.add_argument('file_path', type=str, help='Caminho para o arquivo de saída do grafo')
    parser.add_argument('file_path_resp', type=str, help='Caminho para o arquivo de resposta com articulações, pontes e blocos')

    args = parser.parse_args()

    gerar_grafo(args.num_vertices, args.file_path, args.file_path_resp)
