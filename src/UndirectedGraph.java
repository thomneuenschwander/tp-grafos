import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Set;

public interface UndirectedGraph {

    /*
     * add {v, w} to E(G) | {v, w} ∈ V(G)
    */
    boolean addEdge(int v, int w);

    /*
     * |V|
    */
    int N();

    /*
     * |E|
    */
    int M();

    /*
     * get Neighborhood N(v)
    */
    Collection<Integer> getNeighborhood(int v);

    Collection<Set<Integer>> findBiconnectedComponents();

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
                throw new IllegalArgumentException(
                    "Número de arestas no arquivo (" + edgeCount + ") difere do valor esperado (" + m + ").");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}