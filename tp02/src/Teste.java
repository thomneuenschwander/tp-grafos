import java.util.Arrays;

import algoritmhs.AdjacencyMatrix;
import algoritmhs.FloydWarshall;
import io.GraphReader;

public class Teste {
    static final String FILE_PATH = "pmed_instances/teste.txt";

    public static void main(String[] args) {
        AdjacencyMatrix graph = GraphReader.read(FILE_PATH);
        // System.out.println(graph);

        int[][] fw = FloydWarshall.computeAllPairsShortestPaths(graph);
        var sb = new StringBuilder();
        for (int[] vect : fw)
            sb.append(Arrays.toString(vect) + "\n");
        sb.delete(sb.length() - 1, sb.length());
        System.out.println("[" + sb.toString() + "]");
    }
}
