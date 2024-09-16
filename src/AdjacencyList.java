import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Grafo nao direcionado representado por uma Lista de Adjacência
 * 
 * references:
 * https://www.geeksforgeeks.org/graph-and-its-representations/
 * https://github.com/eugenp/tutorials/blob/master/data-structures/src/main/java/com/baeldung/graph/GraphTraversal.java
 * https://www.baeldung.com/java-graphs
 * Chat GPT 4o
 * 
 * @author Thomas Neuenschwander
 * @since 13/09/2024
 */
public class AdjacencyList implements UndirectedGraph {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public AdjacencyList() {
    }

    @Override
    public List<Integer> getNeighborhood(int v) {
        return graph.getOrDefault(v, new LinkedList<>());
    }

    @Override
    public boolean addEdge(int v, int w) {
        boolean addedToV = false;
        boolean addedToW = false;

        if (!graph.getOrDefault(v, new LinkedList<>()).contains(w)) {
            graph.computeIfAbsent(v, k -> new LinkedList<>()).add(w);
            addedToV = true;
        }

        if (!graph.getOrDefault(w, new LinkedList<>()).contains(v)) {
            graph.computeIfAbsent(w, k -> new LinkedList<>()).add(v);
            addedToW = true;
        }

        return addedToV || addedToW;
    }

    /*
     * 
     * Verifica a existência de dois caminhos internamente disjuntos
     * (ou um ciclo) entre cada par de vértices do bloco
     * 
     */
    public boolean hasTwoInternallyDisjointPaths(int v, int w) {
        if (getNeighborhood(v).size() < 2 || getNeighborhood(w).size() < 2)
            return false;

        int[] disc = new int[N()];
        int[] spawningTree = new int[N()];
        var count = new AtomicInteger();

        for (int i = 0; i < N(); i++) {
            disc[i] = 0;
            spawningTree[i] = -1;
        }

        return performDFS(v, w, disc, count, spawningTree, v);
    }

    private boolean performDFS(int v, int w, int[] disc, AtomicInteger count, int[] pred, int currentV) {
        disc[currentV - 1] = count.incrementAndGet();

        var neighborhood = getNeighborhood(currentV);
        neighborhood.sort(Comparator.naturalOrder());

        for (int u : neighborhood) {
            if (disc[u - 1] == 0) {
                pred[u - 1] = currentV;
                performDFS(v, w, disc, count, pred, u);

            } else if (u != pred[currentV - 1] && currentV < u) {
                int i = (Math.max(disc[u - 1], disc[v - 1]) == disc[u - 1]) ? u : v;
                boolean containsV = false;
                boolean containsW = false;

                while (!(i == -1)) {
                    if(i == v)
                        containsV = true;
                    if(i == w)
                        containsW = true;

                    if(containsV && containsW)
                        return true;

                    i = pred[i - 1];
                }
            }

        }

        return false;
    }

    @Override
    public int N() {
        return graph.size();
    }

    @Override
    public int M() {
        return graph.values().stream().mapToInt(List::size).sum() / 2;
    }

    @Override
    public String toString() {
        return this.graph.toString();
    }

    /*
     * Undirected Graph Format:
     * 
     * N M // n -> number of vertices M -> number of edges
     * v w // Next M lines -> Undirected edge {v, w} that touches 2 vertices v and
     * w.
     * v w
     * (...)
     */
    public static AdjacencyList readGraphFile(String filePath) {
        var adjList = new AdjacencyList();
        UndirectedGraph.readGraphFile(filePath, adjList);
        return adjList;
    }
}