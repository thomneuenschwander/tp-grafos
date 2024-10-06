import java.util.List;
import java.util.Set;

import graph.algorithm.ArticulationsBlockIdentifier;
import graph.algorithm.TarjanBlockIdentifier;
import graph.algorithm.BlockIdentifier;
import graph.algorithm.TwoDisjointPathsBlockIdentifier;
import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class Main {
    static final String GRAPH_FILE_PATH = "graph-files/exemple.txt";

    static final BlockIdentifier twoDisjointPaths = new TwoDisjointPathsBlockIdentifier();
    static final BlockIdentifier articulations = new ArticulationsBlockIdentifier();
    static final BlockIdentifier tarjan = new TarjanBlockIdentifier();

    public static void main(String[] args) {
        UndirectedGraph graph = AdjacencyList.readGraphFile(GRAPH_FILE_PATH);

        System.out.println("---- Testando TwoDisjointPathsBlockIdentifier ----");
        List<Set<Integer>> twoDisjointPathsResult = twoDisjointPaths.identifyBlocks(graph);
        System.out.println("Blocos encontrados (Two Disjoint Paths):");
        twoDisjointPathsResult.forEach(block -> System.out.println(block));

        System.out.println("\n---- Testando ArticulationsBlockIdentifier ----");
        List<Set<Integer>> articulationsResult = articulations.identifyBlocks(graph);
        System.out.println("Blocos encontrados (Articulations):");
        articulationsResult.forEach(block -> System.out.println(block));

        System.out.println("\n---- Testando TarjanBlockIdentifier ----");
        List<Set<Integer>> tarjanResult = tarjan.identifyBlocks(graph);
        System.out.println("Blocos encontrados (Tarjan):");
        tarjanResult.forEach(block -> System.out.println(block));

    }
}
