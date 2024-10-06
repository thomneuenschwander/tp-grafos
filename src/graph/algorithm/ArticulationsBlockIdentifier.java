package graph.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.model.UndirectedGraph;

public class ArticulationsBlockIdentifier implements BlockIdentifier {

    @Override
    public List<Set<Integer>> identifyBlocks(UndirectedGraph graph) {
        List<Integer> articulations = findArticulations(graph);
        
        List<Set<Integer>> blocks = new ArrayList<>();

        boolean[] visited = new boolean[graph.N() + 2];

        for (int articulation : articulations) {
            visited[articulation] = true;
        }

        for (int i = 1; i <= graph.N(); i++) {
            if (!visited[i]) {
                Set<Integer> block = new HashSet<>();
                graph.dfs(i, visited, block);
                blocks.add(block);
            }
        }

        for (Set<Integer> block : blocks) {
            for (int articulation : articulations) {
                if (graph.isConnected(block, articulation, articulations)) {
                    block.add(articulation);
                }
            }
        }
        
        List<int[]> allBridges = UndirectedGraph.findBridges(graph);
        allBridges.forEach(bridge -> blocks.add(Set.of(bridge[0], bridge[1])));

        return blocks;
    }
    
    public static List<Integer> findArticulations(UndirectedGraph graph) {
        List<Integer> articulations = new ArrayList<>();
        for (int i = 1; i <= graph.N(); i++) {
            boolean[] visited = new boolean[graph.N() + 2];
            visited[i] = true;
            if (i == 1) {
                graph.dfs(2, visited, null);
            } else {
                graph.dfs(1, visited, null);
            }
           

            for (int j = 1; j <= graph.N(); j++) {
                if (j != i && !visited[j]) {
                    articulations.add(Integer.valueOf(i));
                    break;
                }
            }
        }
        return articulations;
    }
}
