package graph.algorithm;
import java.util.List;
import java.util.Set;

import graph.model.UndirectedGraph;

public record BlockAnalyzer(BlockIdentifier blockIdentifier) {
    public List<Set<Integer>> analyze(UndirectedGraph graph) {
        return blockIdentifier.identifyBlocks(graph);
    }
}
