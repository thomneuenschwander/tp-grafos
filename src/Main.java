public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/small/simple-conectected.txt";

    public static void main(String[] args) {
        AdjacencyList graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        System.out.println(graph.hasTwoInternallyDisjointPaths(1, 4));
        
        System.out.println(graph);
    }
}
