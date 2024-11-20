package algoritmhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedyKClustering {
    private AdjacencyMatrix adjMatrix;
    private int n;
    private int k;
    private int[][] dist;
    private Map<Integer, List<Integer>> clusters;

    public GreedyKClustering(AdjacencyMatrix adjMatrix) {
        this.adjMatrix = adjMatrix;
        this.n = adjMatrix.N();
        this.k = adjMatrix.K();
        this.dist = FloydWarshall.computeAllPairsShortestPaths(adjMatrix);
        this.clusters = new HashMap<>(k);
    }

    public Map<Integer, List<Integer>> findClusters() {
        List<Integer> centers = selectCenters();

        centers.forEach(c -> clusters.put(c, new ArrayList<>()));

        for (int v = 1; v <= n; v++) {
            int minDistToCenter = Integer.MAX_VALUE;

            int assignedCenter = -1;
            for (int center : centers) {
                if (dist[v - 1][center - 1] < minDistToCenter) {
                    minDistToCenter = dist[v - 1][center - 1];
                    assignedCenter = center;
                }
            }
            clusters.get(assignedCenter).add(v);
        }

        return clusters;
    }

    private List<Integer> selectCenters() {
        List<Integer> kCenters = new ArrayList<>(k);
        boolean[] isCenter = new boolean[n];

        int firstCluster = selectVertexWithHighestDegree();
        kCenters.add(firstCluster);
        isCenter[firstCluster - 1] = true;

        int[] minDist = dist[firstCluster - 1].clone();

        while (kCenters.size() < k) {
            int maxMinDist = -1;
            int maxVertex = -1;
            for (int v = 1; v <= n; v++) {
                if (!isCenter[v - 1] && minDist[v - 1] > maxMinDist) {
                    maxMinDist = minDist[v - 1];
                    maxVertex = v;
                }
            }
            kCenters.add(maxVertex);
            isCenter[maxVertex - 1] = true;

            for (int v = 1; v <= n; v++)
                if (!isCenter[v - 1])
                    minDist[v - 1] = Math.min(minDist[v - 1], dist[v - 1][maxVertex - 1]);
        }
        return kCenters;
    }

    private int selectVertexWithHighestDegree() {
        int maxDegree = -1;
        int selectedVertex = -1;

        for (int v = 1; v <= n; v++) {
            int degree = 0;
            for (int w = 1; w <= n; w++)
                if (adjMatrix.getAdjMatrix()[v - 1][w - 1] != Integer.MAX_VALUE)
                    degree++;

            if (degree > maxDegree) {
                maxDegree = degree;
                selectedVertex = v;
            }
        }
        return selectedVertex;
    }

    public Map<Integer, List<Integer>> getClusters() {
        return clusters;
    }

    public int calculateRadius() {
        int maxRadius = 0;

        for (var entry : clusters.entrySet()) {
            int center = entry.getKey();
            List<Integer> vertices = entry.getValue();

            int clusterRadius = 0;
            for (int v : vertices) 
                clusterRadius = Math.max(clusterRadius, dist[center - 1][v - 1]);

            maxRadius = Math.max(maxRadius, clusterRadius);
        }

        return maxRadius;
    }
}
