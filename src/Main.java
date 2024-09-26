import java.util.List;
import java.util.Set;

import graph.algorithm.BlockIdentifier;
import graph.algorithm.TwoDisjointPathsBlockIdentifier;
import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/timer.txt";

    public static void main(String[] args) {
        UndirectedGraph graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        BlockIdentifier identifier = new TwoDisjointPathsBlockIdentifier();

        List<Set<Integer>> blocks = identifier.identifyBlocks(graph);

        blocks.forEach(System.out::println);
    }
}
