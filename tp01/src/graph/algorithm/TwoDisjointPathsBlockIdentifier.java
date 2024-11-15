package graph.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import graph.model.UndirectedGraph;

public class TwoDisjointPathsBlockIdentifier implements BlockIdentifier {
    private UndirectedGraph graph;
    private List<Set<Integer>> allCycles;
    private List<int[]> allBridges;

    @Override
    public List<Set<Integer>> identifyBlocks(UndirectedGraph graph) {
        this.graph = graph;
        allCycles = findAllCycles();
        allBridges = UndirectedGraph.findBridges(graph);

        List<Set<Integer>> blocks = new LinkedList<>();

        for (int i = 1; i <= graph.N(); i++) {
            Set<Integer> block = new HashSet<>();
            for (int j = i + 1; j <= graph.N(); j++) {
                if (hasTwoDisjointPaths(i, j)) {
                    block.add(i);
                    block.add(j);
                }
            }
            if (!block.isEmpty())
                blocks.add(block);
        }
        List<Set<Integer>> trimBlocks = removeSubsets(blocks);
        allBridges.forEach(bridge -> trimBlocks.add(Set.of(bridge[0], bridge[1])));
        return trimBlocks;
    }

    private List<Set<Integer>> removeSubsets(List<Set<Integer>> blocks) {
        List<Set<Integer>> filteredBlocks = new LinkedList<>();

        for (Set<Integer> block : blocks) {
            boolean isSubset = false;
            for (Set<Integer> otherBlock : blocks) {
                if (!block.equals(otherBlock) && otherBlock.containsAll(block)) {
                    isSubset = true;
                    break;
                }
            }

            if (!isSubset)
                filteredBlocks.add(block);
        }

        return filteredBlocks;
    }

    public boolean hasTwoDisjointPaths(int u, int v) {
        for (Set<Integer> cycle : allCycles) {
            if (cycle.contains(u) && cycle.contains(v))
                return true;
        }
        return false;
    }

    public List<Set<Integer>> findAllCycles() {
        List<Set<Integer>> allCycles = new ArrayList<>();
        Set<List<Integer>> uniqueCycles = new HashSet<>();

        graph.V().forEach(v -> {
            Set<Integer> visited = new HashSet<>();
            findCycles(v, v, new LinkedHashSet<>(), visited, allCycles, uniqueCycles);
        });

        return allCycles;
    }

    private void findCycles(int r, int v, Set<Integer> path, Set<Integer> visited, List<Set<Integer>> allCycles,
            Set<List<Integer>> uniqueCycles) {
        path.add(v);
        visited.add(v);

        for (int w : graph.getNeighborhood(v)) {
            if (w == r && path.size() > 2) {
                List<Integer> cycle = new ArrayList<>(path);
                Collections.sort(cycle);

                if (uniqueCycles.add(cycle))
                    allCycles.add(new HashSet<>(path));

            } else if (!visited.contains(w) && w > r)
                findCycles(r, w, path, visited, allCycles, uniqueCycles);
        }

        visited.remove(v);
        path.remove(v);
    }
}
