# Trabalho Prático N.02 - K centros

Este trabalho tem como principal objetivo discutir duas abordagens para o problema de K-centers: uma solução exata baseada em força bruta, que analisa todas as combinações possíveis de centros, e uma solução aproximada gulosa. Além de apresentar as abordagens, este trabalho também discute os resultados obtidos em experimentos computacionais, comparando o tempo de execução, o consumo de memória e a precisão das soluções encontradas por cada método.

## Previsão do Tempo de Execução do Brute Force para grandes Instâncias

É utilizado um [modelo de Regressão](./pmed_brute_force_prev.py) Linear para a previsão. Dessa forma, a complexidade do [Brute Force](./src/algorithms/BruteForceKCenter.java) é expressa em logaritimo para linearizar a relação não linear entre a complexidade e o tempo de execução.

O(C(N,K) * N * K) = log(C(N,K)) + log(K) + log(N)
