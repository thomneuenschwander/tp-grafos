# Trabalho Prático N.02 - K centros





## Previsão do Tempo de Execução do Brute Force para grandes Instâncias

É utilizado um [modelo de Regressão](./pmed_brute_force_prev.py) Linear para a previsão. Dessa forma, a complexidade do [Brute Force](./src/algorithms/BruteForceKCenter.java) é expressa em logaritimo para linearizar a relação não linear entre a complexidade e o tempo de execução.

O(C(N,K) * N * K) = log(C(N,K)) + log(K) + log(N)