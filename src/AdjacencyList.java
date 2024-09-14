import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public AdjacencyList() { }

    @Override
    public List<Integer> getNeighborhood(int v) {
        return graph.getOrDefault(v, new LinkedList<>());
    }


    @Override
    public boolean addEdge(int v, int w) {
        boolean addedToU = false;
        boolean addedToV = false;

        if (!graph.getOrDefault(v, new LinkedList<>()).contains(w)) {
            graph.computeIfAbsent(v, k -> new LinkedList<>()).add(w);
            addedToU = true;
        }

        if (!graph.getOrDefault(w, new LinkedList<>()).contains(v)) {
            graph.computeIfAbsent(w, k -> new LinkedList<>()).add(v);
            addedToV = true;
        }

        return addedToU || addedToV;
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
     *  N M        // n -> number of vertices  M -> number of edges  
     *  v w       // Next M lines -> Undirected edge {v, w} that touches 2 vertices v and w.
     *  v w              
     * (...)         
    */
    public static AdjacencyList readGraphFile(String filePath) {
        var adjList = new AdjacencyList();
        UndirectedGraph.readGraphFile(filePath, adjList);
        return adjList;
    }
}