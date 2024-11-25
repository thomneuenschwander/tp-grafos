import algorithms.Graph;
import algorithms.KCenterAlgorithm;
import io.CLI;
import io.GraphReader;

public class Main {
    public static void main(String[] args) {
        try {
            CLI argsParser = new CLI(args);

            String filePath = argsParser.getFilePath();
            KCenterAlgorithm.Type algorithmType = argsParser.getAlgorithm();
            boolean verbose = argsParser.isVerbose();

            if (verbose) {
                System.out.println("File Path: " + filePath);
                System.out.println("Algorithm: " + algorithmType);
                System.out.println("Verbose Mode: ON");
            }

            GraphReader reader = new GraphReader(filePath, verbose);

            Graph graph = reader.readGraph();
            int k = reader.totalCenters();

            KCenterAlgorithm algorithm = algorithmType.createInstance(graph);
            algorithm.initialize(k);
            algorithm.computeCenters();

            if(verbose)
                System.out.print("Radius: ");
            System.out.println(algorithm.getRadius());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            CLI.usage();
        }
    }
}