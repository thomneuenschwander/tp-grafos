# Trabalhos Práticos de Grafos

Este repositório contém códigos desenvolvidos para a disciplina de Grafos do curso de Ciência da Computação da PUC Minas.

## Trabalho Prático N.01

Neste trabalho prático, foram implementados três métodos para identificar todos os blocos de um grafo não direcionado. Cada método aborda a resolução do problema de uma maneira distinta, e todos foram desenvolvidos com o intuito de testar a robustez das abordagens e compreender a teoria de componentes biconexos.

### Grafo Biconexo

Um grafo **biconexo** é um grafo não direcionado que possui as seguintes características:

- Para qualquer par de vértices, existem pelo menos **dois caminhos internamente disjuntos**, ou seja, caminhos sem vértices internos em comum.

- Em um grafo biconexo, **não existem articulações** (vértices que, se removidos, desconectam o grafo).

- Todo vértice de um grafo biconexo tem grau maior que 1.

### Bloco

**Bloco:** Os blocos são componentes biconexos. Ou seja, subgrafos maximais de G que sejam biconexos em vértices, ou isomorfos a K2. Um grafo biconexo possui apenas 1 unico bloco.

### Métodos Implementados

Foram desenvolvidos três métodos desafiados para identificar os blocos de um grafo não direcionado:

#### **i.** Um método que verifique a existência de dois caminhos internamente disjuntos

Este método verifica se, para cada par de vértices em um grafo, existem dois caminhos internamente disjuntos. Caminhos internamente disjuntos são aqueles que não compartilham nenhum vértice intermediário.

#### **ii.** 

Este método verifica a conectividade do grafo ao testar a remoção de cada vértice. Uma articulação é um vértice cuja remoção desconecta o grafo. O algoritmo identifica todos os vértices que são articulações, ou seja, aqueles que, se removidos, aumentariam o número de componentes conectados no grafo. Essas articulações são úteis para identificar pontos críticos de falha na conectividade do grafo.

#### **iii.** 

O algoritmo de Tarjan é um método eficiente proposto por Robert Tarjan em 1972 para encontrar componentes biconexos e articulações em um grafo. O algoritmo usa uma abordagem de busca em profundidade (DFS) para identificar todos os blocos e articulações em tempo linear, ou seja, com complexidade O(V + E), onde V é o número de vértices e E é o número de arestas. Este é um dos algoritmos mais eficientes para a identificação de blocos em grafos não direcionados.


## 🧩 Colaboradores
| <img src="https://github.com/andreeluis.png" width="100" height="100" alt="André Luís"/> | <img src="https://github.com/thomneuenschwander.png" width="100" height="100" alt="Thomas Neuenschwander"/> |
|:---:|:---:|
| [Thomas <br> Neuenschwander](https://github.com/andreeluis) | [Thiago <br> Rezende](https://github.com/ThiagoRezendeAguiar) | [Luigi <br> Louback](https://github.com/LuigiLouback) |
