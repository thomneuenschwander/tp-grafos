import java.util.List;
import java.util.Set;

import graph.algorithm.ArticulationsBlockIdentifier;
import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class Teste {
    static final String GRAPH_FILE_PATH = "graph-files/timer.txt";

    public static void main(String[] args) {
        UndirectedGraph graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        ArticulationsBlockIdentifier identifier = new ArticulationsBlockIdentifier();

        List<Set<Integer>> blocks = identifier.identifyBlocks(graph);

        blocks.forEach(System.out::println);
    }
}
