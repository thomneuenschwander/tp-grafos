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
        System.out.println("Articulations: " + articulations);
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

        for (int i = 0; i < articulations.size(); i++) {
            for (int j = i + 1; j < articulations.size(); j++) {
                int articulation1 = articulations.get(i);
                int articulation2 = articulations.get(j);
                
                List<Integer> neighbors1 = (List<Integer>) graph.getNeighborhood(articulation1);
                List<Integer> neighbors2 = (List<Integer>) graph.getNeighborhood(articulation2);

                boolean isPonte = true;
                
                for (int neighbor: neighbors1) {
                    if (neighbors2.contains(neighbor)) {
                        isPonte = false;
                        break;
                    }
                }
                
                if (isPonte) {
                    Set<Integer> block = new HashSet<>();
                    block.add(articulation1);
                    block.add(articulation2);
                    blocks.add(block); 
                }
            }
        }
        
        

        return blocks;
    }
    
    private List<Integer> findArticulations(UndirectedGraph graph) {
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
