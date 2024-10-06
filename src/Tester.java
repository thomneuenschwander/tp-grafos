import graph.algorithm.ArticulationsBlockIdentifier;
import graph.algorithm.BlockIdentifier;
import graph.algorithm.TarjanBlockIdentifier;
import graph.algorithm.TwoDisjointPathsBlockIdentifier;
import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class Tester {
    static final BlockIdentifier twoDisjointPaths = new TwoDisjointPathsBlockIdentifier();
    static final BlockIdentifier articulations = new ArticulationsBlockIdentifier();
    static final BlockIdentifier tarjan = new TarjanBlockIdentifier();

    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException(errorMessage);

        final String implementation = args[0];
        final String graphFilePath = args[1];

        UndirectedGraph graph = AdjacencyList.readGraphFile(graphFilePath);

        BlockIdentifier blockIdentifier = switch (implementation) {
            case "1" -> twoDisjointPaths;
            case "2" -> articulations;
            case "3" -> tarjan;
            default -> throw new IllegalArgumentException(errorMessage);
        };

        blockIdentifier.identifyBlocks(graph).forEach(System.out::println);
    }

    private static final String errorMessage = "Usage: <'1' for twoDisjointPaths, '2' for articulations, or '3' for tarjan> and <graph file pah>";
}
