public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/timer.txt";

    public static void main(String[] args) {
        UndirectedGraph graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        System.out.println("Biconnected Components with internally disjoint paths:");
        graph.findBiconnectedComponents().forEach(System.out::println);
    }
}
