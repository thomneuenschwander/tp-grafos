import java.util.List;
import java.util.Map;

import algoritmhs.AdjacencyMatrix;
import algoritmhs.GreedyKClustering;
import io.GraphReader;

public class Main {
    static final String FILE_PATH = "pmed_instances/pmed2.txt";

    public static void main(String[] args) {
        AdjacencyMatrix graph = GraphReader.read(FILE_PATH);

        GreedyKClustering greedy = new GreedyKClustering(graph);

        Map<Integer, List<Integer>> clusters = greedy.findClusters();

        System.out.println("\n=== Clustering Result ===");
        System.out.println("Clusters: " + clusters.size() + " - Radius: "
                + greedy.calculateRadius() + "\n");

        clusters.forEach((center, vertices) -> System.out.println(
                "Center: " + center + ", number of vertices " + vertices.size() + ", vertices=" + vertices));
    }
}