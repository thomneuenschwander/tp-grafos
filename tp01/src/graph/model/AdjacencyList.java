package graph.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyList implements UndirectedGraph {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public AdjacencyList() { }

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

    @Override
    public boolean isConnected(Set<Integer> block, int articulation, List<Integer> articulations){
        for (int vertex : block) {
            if (graph.get(vertex).contains(articulation) && !articulations.contains(vertex)) {
                return true;
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
        return graph.toString();
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

    @Override
    public List<Integer> V() {
        return new ArrayList<>(graph.keySet());
    }
}