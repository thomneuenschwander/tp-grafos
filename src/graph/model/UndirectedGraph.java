package graph.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UndirectedGraph {
    /*
     * |V|
    */
    int N();

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
    Collection<Integer> getNeighborhood(int v);

    boolean isConnected(Set<Integer> block, int articulation, List<Integer> articulations);

    /**
     * Checks if there is a path between two vertices u and v.
     * Uses Depth-First Search (DFS) to verify connectivity between the vertices.
     * 
     * @param u The source vertex.
     * @param v The target vertex.
     * @return true if there is a path between u and v, false otherwise.
    */
    default boolean isReachable(int u, int v) {
        boolean[] visited = new boolean[N() + 1];
        return dfs(u, v, visited);
    }

    private boolean dfs(int u, int v, boolean[] visited) {
        if (u == v)
            return true;
        visited[u] = true;

        for (int neighbor : getNeighborhood(u)) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, v, visited))
                    return true;
            }
        }
        return false;
    }

    default void dfs(int v, boolean[] visited, Set<Integer> block) {
        visited[v] = true;

        if(block != null) block.add(v);

        for (int w : getNeighborhood(v)) {
            if (!visited[w]) {
                dfs(w, visited, block);
            }
        }
    }

    /*
     * Undirected Graph Format:
     * 
     *  N M        // n -> number of vertices  M -> number of edges  
     *  v w       // Next M lines -> Undirected edge {v, w} that touches 2 vertices v and w.
     *  v w              
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

                if(v < 1 || v > n || w < 1 || w > n)
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