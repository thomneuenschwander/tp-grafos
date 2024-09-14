public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/graph_test.txt";

    public static void main(String[] args) {
        AdjacencyList graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);
        
        System.out.println(graph);
    }
}
