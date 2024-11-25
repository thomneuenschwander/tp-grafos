import algorithms.BruteForceKCenter;
import algorithms.Graph;
import algorithms.KCenterAlgorithm;
import io.GraphReader;

public class Main {
    static final String FILE_PATH = "pmed_instances/pmed6.txt";

    public static void main(String[] args) {

        GraphReader reader = new GraphReader(FILE_PATH, true);

        Graph graph = reader.readGraph();
        int k = reader.totalCenters();

        KCenterAlgorithm bruteForce = new BruteForceKCenter(graph);
        bruteForce.initialize(k);
        bruteForce.computeCenters();

        System.out.println(bruteForce.getRadius());

    }
}