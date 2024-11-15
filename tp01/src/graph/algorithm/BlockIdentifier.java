package graph.algorithm;
import java.util.List;
import java.util.Set;

import graph.model.UndirectedGraph;

public interface BlockIdentifier {
    List<Set<Integer>> identifyBlocks(UndirectedGraph graph);
}
