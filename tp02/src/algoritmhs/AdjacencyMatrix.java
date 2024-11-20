package algoritmhs;

public class AdjacencyMatrix extends WeightedClusterGraph {
    private int[][] adjMatrix;

    public AdjacencyMatrix(int n, int m, int k) {
        super(n, m, k);
        this.adjMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                adjMatrix[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
        }
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    @Override
    public boolean addEdge(int u, int v, int weight) {
        if (!isVertexValid(v) || !isVertexValid(u) || weight < 0)
            return false;

        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight;
        return true;
    }

}
