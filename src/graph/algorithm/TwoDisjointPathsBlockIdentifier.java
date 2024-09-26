package graph.algorithm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.model.UndirectedGraph;

public class TwoDisjointPathsBlockIdentifier implements BlockIdentifier {
    @Override
    public List<Set<Integer>> identifyBlocks(UndirectedGraph graph) {
        List<Set<Integer>> blocks = new ArrayList<>();
        boolean[][] processed = new boolean[graph.N() + 1][graph.N() + 1];

        for (int u = 1; u <= graph.N(); u++) {
            for (int v = u + 1; v <= graph.N(); v++) {
                if (!processed[u][v]) {
                    if (hasTwoDisjointPaths(graph, u, v)) {
                        Set<Integer> componentU = null;
                        Set<Integer> componentV = null;

                        for (Set<Integer> component : blocks) {
                            if (component.contains(u))
                                componentU = component;

                            if (component.contains(v))
                                componentV = component;
                        }

                        switch ((componentU != null ? 1 : 0) + (componentV != null ? 2 : 0)) {
                            case 0 -> {
                                Set<Integer> newComponent = new HashSet<>();
                                newComponent.add(u);
                                newComponent.add(v);
                                blocks.add(newComponent);
                            }

                            case 1 -> componentU.add(v);

                            case 2 -> componentV.add(u);

                            case 3 -> {
                                if (componentU != componentV) {
                                    componentU.addAll(componentV);
                                    blocks.remove(componentV);
                                }
                            }
                        }

                    }
                    processed[u][v] = true;
                    processed[v][u] = true;
                }
            }
        }
        return blocks;
    }

    private boolean hasTwoDisjointPaths(UndirectedGraph graph, int u, int v) {
        if (!graph.isReachable(u, v))
            return false;

        List<Integer> path1 = findPath(graph, u, v);
        for (int i = 0; i < path1.size() - 1; i++) {
            int src = path1.get(i);
            int dest = path1.get(i + 1);
            graph.getNeighborhood(src).remove(Integer.valueOf(dest));
            graph.getNeighborhood(dest).remove(Integer.valueOf(src));
        }

        boolean result = graph.isReachable(u, v);

        for (int i = 0; i < path1.size() - 1; i++) {
            int src = path1.get(i);
            int dest = path1.get(i + 1);
            graph.getNeighborhood(src).add(dest);
            graph.getNeighborhood(dest).add(src);
        }

        return result;
    }

    private List<Integer> findPath(UndirectedGraph graph, int u, int v) {
        List<Integer> path = new ArrayList<>();
        findPath(graph, u, v, new boolean[graph.N() + 1], path);
        return path;
    }

    private boolean findPath(UndirectedGraph graph, int u, int v, boolean[] visited, List<Integer> path) {
        path.add(u);
        if (u == v)
            return true;
        visited[u] = true;

        for (int neighbor : graph.getNeighborhood(u)) {
            if (!visited[neighbor]) {
                if (findPath(graph, neighbor, v, visited, path))
                    return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
