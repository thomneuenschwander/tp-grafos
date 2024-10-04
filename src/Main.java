import java.util.List;
import java.util.Set;
import graph.algorithm.BiconnectedComponentsTarjan;
import graph.algorithm.BlockIdentifier;
import graph.algorithm.TwoDisjointPathsBlockIdentifier;
import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/teste.txt";

    public static void main(String[] args) {
        UndirectedGraph graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        BlockIdentifier identifier = new TwoDisjointPathsBlockIdentifier();

        List<Set<Integer>> blocks = identifier.identifyBlocks(graph);


        blocks.forEach(System.out::println);

        BiconnectedComponentsTarjan identificador = new BiconnectedComponentsTarjan((AdjacencyList) graph);
        List<Set<Integer>> blocos = identificador.identifyBlocks(graph);
        blocos.forEach(System.out::println);
        
    }
}
