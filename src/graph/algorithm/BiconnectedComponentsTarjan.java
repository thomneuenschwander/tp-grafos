package graph.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.model.AdjacencyList;
import graph.model.UndirectedGraph;

public class BiconnectedComponentsTarjan implements BlockIdentifier {
    private boolean[] visited;
    private int[] disc;
    private int[] low;
    private int[] parent;
    private ArrayList<int[]> edgeStack;
    private int time;
    private ArrayList<ArrayList<int[]>> biconnectedComponents;

    public BiconnectedComponentsTarjan(AdjacencyList graph) {
        int V = graph.N()+1;
        visited = new boolean[V];
        disc = new int[V];
        low = new int[V];
        parent = new int[V];
        edgeStack = new ArrayList<>();
        biconnectedComponents = new ArrayList<>();
        time = 0;

        for (int v = 0; v < V; v++) {
            parent[v] = -1;
            if (!visited[v]) {
                biconnect(graph, v);
            }
        }
    }

    private void biconnect(AdjacencyList graph, int v) {
        visited[v] = true;
        disc[v] = low[v] = ++time;

        for (int w : graph.getNeighborhood(v)) {
            if (!visited[w]) {
                parent[w] = v;
                edgeStack.add(new int[]{v, w});
                biconnect(graph, w);

                low[v] = Math.min(low[v], low[w]);

                if (low[w] >= disc[v]) {
                    ArrayList<int[]> component = new ArrayList<>();
                    int[] edge;
                    while (!edgeStack.isEmpty()) {
                        edge = edgeStack.remove(edgeStack.size() - 1);
                        component.add(edge);
                        if (edge[0] == v && edge[1] == w) {
                            break;
                        }
                    }
                    biconnectedComponents.add(component);
                }
            } else if (w != parent[v] && disc[w] < disc[v]) {
                low[v] = Math.min(low[v], disc[w]);
                edgeStack.add(new int[]{v, w});
            }
        }
    }

 
    @Override
    public List<Set<Integer>> identifyBlocks(UndirectedGraph graph) {
        List<Set<Integer>> blocks = new ArrayList<>();
        for (ArrayList<int[]> component : biconnectedComponents) {
            Set<Integer> block = new HashSet<>();
            for (int[] edge : component) {
                block.add(edge[0]);
                block.add(edge[1]);
            }
            blocks.add(block);
        }
        return blocks;
    }
}
