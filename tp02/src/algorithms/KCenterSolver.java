package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class KCenterSolver implements KCenterAlgorithm {
    protected Set<Integer> centers;
    protected int radius;
    protected int K;
    protected int N;
    protected Graph graph;
    protected int[][] dist;
    protected Map<Integer, List<Integer>> clusters;
    protected final int NOT_ASSIGN = Integer.MAX_VALUE;

    public KCenterSolver(Graph graph) {
        this.N = graph.N();
        this.graph = graph;
        this.dist = graph.computeAllPairsShortestPaths();
        this.radius = NOT_ASSIGN;
        this.K = NOT_ASSIGN;
    }

    @Override
    public void initialize(int k) {
        this.K = k;
        this.centers = new HashSet<>(K);
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public Set<Integer> getCenters() {
        return centers;
    }

    @Override
    public Map<Integer, List<Integer>> getClusters() {
        if (clusters == null)
            assignClusters();

        return clusters;
    }

    protected void assignClusters() {
        clusters = new HashMap<>(K);
        centers.forEach(c -> clusters.put(c, new ArrayList<>()));

        for (int v = 0; v < N; v++) {
            int closestCenter = -1;
            int minDist = Integer.MAX_VALUE;
            for (int center : centers) {
                if (dist[v][center] < minDist) {
                    minDist = dist[v][center];
                    closestCenter = center;
                }
            }
            clusters.get(closestCenter).add(v + 1);
        }
    }
}
