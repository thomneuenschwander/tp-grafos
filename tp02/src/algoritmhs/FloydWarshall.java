package algoritmhs;

public class FloydWarshall {
    public static int[][] computeAllPairsShortestPaths(AdjacencyMatrix graph) {
        int n = graph.N();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++)
            dist[i] = graph.getAdjMatrix()[i].clone();

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE &&
                            dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        return dist;
    }
}
