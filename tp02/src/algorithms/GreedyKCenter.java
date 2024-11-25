package algorithms;

public class GreedyKCenter extends KCenterSolver {

    public GreedyKCenter(Graph graph) {
        super(graph);
    }

    @Override
    public void computeCenters() {
        int firstCenter = selectVertexWithHighestDegree();
        centers.add(firstCenter);

        int[] minDist = dist[firstCenter - 1].clone();

        while (centers.size() < K) {
            int maxMinDist = -1;
            int maxVertex = -1;
            for (int v = 1; v <= N; v++) {
                if (!centers.contains(v) && minDist[v - 1] > maxMinDist) {
                    maxMinDist = minDist[v - 1];
                    maxVertex = v;
                }
            }
            centers.add(maxVertex);

            for (int v = 0; v < N; v++)
                if (!centers.contains(v + 1))
                    minDist[v] = Math.min(minDist[v], dist[v][maxVertex - 1]);
        }
    }

    private int selectVertexWithHighestDegree() {
        int maxDegree = -1;
        int selectedVertex = -1;

        for (int v = 0; v < N; v++) {
            int degree = 0;
            for (int w = 0; w < N; w++)
                if (graph.getAdjMatrix()[v][w] != Integer.MAX_VALUE)
                    degree++;

            if (degree > maxDegree) {
                maxDegree = degree;
                selectedVertex = v + 1;
            }
        }
        return selectedVertex;
    }

    @Override
    public int getRadius() {
        if (radius == NOT_ASSIGN)
            radius = calculateRadius();
        return radius;
    }

    public int calculateRadius() {
        int radius = 0;

        for (int v = 0; v < N; v++) {
            int minDistToCenter = Integer.MAX_VALUE;

            for (int center : centers)
                minDistToCenter = Math.min(minDistToCenter, dist[v][center - 1]);

            radius = Math.max(radius, minDistToCenter);
        }

        return radius;
    }
}
