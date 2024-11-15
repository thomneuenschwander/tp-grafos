package graph.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UndirectedGraph {
    /*
     * |V|
     */
    int N();

    List<Integer> V();

    /*
     * |E|
     */
    int M();

    /*
     * add {v, w} to E(G) | {v, w} ∈ V(G)
     */
    boolean addEdge(int v, int w);

    /*
     * get Neighborhood N(v)
     */
    List<Integer> getNeighborhood(int v);

    boolean isConnected(Set<Integer> block, int articulation, List<Integer> articulations);

    default void dfs(int v, boolean[] visited, Set<Integer> block) {
        visited[v] = true;

        if (block != null) block.add(v);

        for (int w : getNeighborhood(v)) 
            if (!visited[w]) 
                dfs(w, visited, block);
    }

    public static List<int[]> findBridges(UndirectedGraph graph) {
        List<int[]> bridges = new ArrayList<>();
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> discoveryTime = new HashMap<>();
        Map<Integer, Integer> low = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        int time = 0;

        for (Integer vertex : graph.V()) {
            visited.put(vertex, false);
            discoveryTime.put(vertex, -1);
            low.put(vertex, -1);
            parent.put(vertex, -1);
        }

        for (Integer vertex : graph.V()) 
            if (!visited.get(vertex)) 
                dfs(graph, vertex, visited, discoveryTime, low, parent, bridges, time);
        
        return bridges;
    }

    private static void dfs(UndirectedGraph graph, int u, Map<Integer, Boolean> visited,
            Map<Integer, Integer> discoveryTime, Map<Integer, Integer> low, Map<Integer, Integer> parent,
            List<int[]> bridges, int time) {
        visited.put(u, true);
        discoveryTime.put(u, time);
        low.put(u, time);
        time++;

        for (Integer v : graph.getNeighborhood(u)) {
            if (!visited.get(v)) {
                parent.put(v, u);
                dfs(graph, v, visited, discoveryTime, low, parent, bridges, time);

                low.put(u, Math.min(low.get(u), low.get(v)));

                if (low.get(v) > discoveryTime.get(u))
                    bridges.add(new int[] { u, v });
            } else if (v != parent.get(u))
                low.put(u, Math.min(low.get(u), discoveryTime.get(v)));
        }
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
    public static void readGraphFile(String filePath, UndirectedGraph graph) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String[] firstLine = br.readLine().trim().split("\\s+");
            int n = Integer.parseInt(firstLine[0]);
            int m = Integer.parseInt(firstLine[1]);

            int edgeCount = 0;

            while (br.ready()) {
                String[] line = br.readLine().trim().split("\\s+");
                int v = Integer.parseInt(line[0]);
                int w = Integer.parseInt(line[1]);

                if (v < 1 || v > n || w < 1 || w > n)
                    throw new IllegalArgumentException("Vértices devem estar no intervalo de 1 a " + n);

                graph.addEdge(v, w);
                edgeCount++;
            }
            if (edgeCount != m)
                throw new IllegalStateException(
                        "Número de arestas no arquivo (" + edgeCount + ") difere do valor esperado (" + m + ").");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}