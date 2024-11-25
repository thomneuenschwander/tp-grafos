package algorithms;

import java.util.Arrays;

public class Graph {
    private int N;
    private int M;
    private int[][] adjMatrix;
    private static final int INFINITY = Integer.MAX_VALUE;

    public Graph(int numberOfVertices) {
        this.N = numberOfVertices;
        this.adjMatrix = new int[N][N];
        for (int[] row : adjMatrix)
            Arrays.fill(row, INFINITY);
        for (int i = 0; i < N; i++)
            adjMatrix[i][i] = 0;
    }

    public boolean addEdge(int u, int v, int weight) {
        if (!isVertexValid(v) || !isVertexValid(u) || weight < 0)
            throw new IllegalArgumentException("Invalid edge parameters");
        u--;
        v--;
        
        // if (adjMatrix[u][v] != INFINITY || adjMatrix[v][u] != INFINITY)
        // return false;

        adjMatrix[u][v] = weight;
        adjMatrix[v][u] = weight;
        return true;
    }

    protected boolean isVertexValid(int v) {
        return v >= 1 || v <= N;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int[] vect : adjMatrix)
            sb.append(Arrays.toString(vect) + "\n");
        sb.delete(sb.length() - 1, sb.length());
        return "[" + sb.toString() + "]";
    }

    public int[][] computeAllPairsShortestPaths() {
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++)
            dist[i] = adjMatrix[i].clone();

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE &&
                            dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        return dist;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }
}
