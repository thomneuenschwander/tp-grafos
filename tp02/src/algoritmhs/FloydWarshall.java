package algoritmhs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FloydWarshall {
    public int n;
    public int m;
    public int k;
    public int[][] adjMatrix;

    public FloydWarshall(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            readFileHeader(br);
            adjMatrix = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    adjMatrix[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
            }

            for (int i = 0; i < m; i++) {
                String edgeLine = br.readLine().trim();
                String[] edgeLineParts = edgeLine.split(" ");
                if (edgeLineParts.length != 3)
                    throw new IllegalStateException("Invalid file edge format: " + edgeLine);

                int v = Integer.parseInt(edgeLineParts[0]) - 1;
                int w = Integer.parseInt(edgeLineParts[1]) - 1;
                int weight = Integer.parseInt(edgeLineParts[2]);

                if (v < 0 || v >= n || w < 0 || w >= n)
                    throw new IllegalArgumentException("Invalid vertex: " + v + " or " + w);

                adjMatrix[v][w] = weight;
                adjMatrix[w][v] = weight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFileHeader(BufferedReader br) throws IOException {
        String header = br.readLine().trim();
        if (header == null || header.isEmpty())
            throw new IllegalStateException("Empty header");

        String[] headerParts = header.split(" ");
        if (headerParts.length < 3)
            throw new IllegalStateException("Invalid file header. Expected: 'n m k'");

        n = Integer.parseInt(headerParts[0]);
        m = Integer.parseInt(headerParts[1]);
        k = Integer.parseInt(headerParts[2]);
    }

    public int[][] computeAllPairsShortestPaths() {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++)
            dist[i] = adjMatrix[i].clone();

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE &&
                            dist[i][k] + dist[k][j] < dist[i][j]) 
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        return dist;
    }
}
