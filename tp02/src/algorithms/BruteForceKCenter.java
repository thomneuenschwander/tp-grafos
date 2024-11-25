package algorithms;

public class BruteForceKCenter extends KCenterSolver {

    public BruteForceKCenter(Graph graph) {
        super(graph);
    }

    @Override
    public void computeCenters() {
        int[] combination = new int[K];
        for (int i = 0; i < K; i++)
            combination[i] = i + 1;

        int[] minDist = new int[N + 1];

        while (true) {
            for (int i = 1; i <= N; i++)
                minDist[i] = Integer.MAX_VALUE;

            for (int c = 0; c < K; c++) {
                int center = combination[c];
                for (int i = 1; i <= N; i++) {
                    int d = dist[i - 1][center - 1];
                    if (d < minDist[i])
                        minDist[i] = d;
                }
            }

            int r = 0;
            for (int i = 1; i <= N; i++)
                if (minDist[i] > r)
                    r = minDist[i];

            if (r < radius)
                radius = r;

            int i;
            for (i = K - 1; i >= 0; i--) {
                if (combination[i] < N - K + i + 1) {
                    combination[i]++;
                    for (int j = i + 1; j < K; j++)
                        combination[j] = combination[j - 1] + 1;

                    break;
                }
            }
            if (i < 0)
                break;
        }
    }
}
