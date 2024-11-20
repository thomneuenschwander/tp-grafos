package algoritmhs;

public abstract class WeightedClusterGraph {
    private final int n;
    private final int m;
    private final int k;

    public WeightedClusterGraph(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }

    public int N() {
        return n;
    }

    public int M() {
        return m;
    }

    public int K() {
        return k;
    }

    abstract boolean addEdge(int u, int v, int weight);

    protected boolean isVertexValid(int v) {
        return v >= 1 || v <= n;
    }
}
