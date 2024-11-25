package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import algorithms.Graph;

public class GraphReader {
    private final String filePath;
    private int n;
    private int m;
    private int k;
    private boolean verbose;
    private static final Logger LOGGER = Logger.getLogger(GraphReader.class.getName());

    public GraphReader(String filePath) {
        this(filePath, -1, -1, -1, false);
    }

    public GraphReader(String filePath, boolean verbose) {
        this(filePath, -1, -1, -1, verbose);
    }

    private GraphReader(String filePath, int n, int m, int k, boolean verbose) {
        this.filePath = filePath;
        this.n = n;
        this.m = m;
        this.k = k;
        this.verbose = verbose;
    }

    public Graph readGraph() {
        Graph graph = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine().trim();
            if (header == null || header.isEmpty())
                throw new IllegalStateException("Empty header");

            String[] headerParts = header.split(" ");
            if (headerParts.length < 3)
                throw new IllegalStateException("Invalid file header. Expected: 'n m k'");

            this.n = Integer.parseInt(headerParts[0]);
            this.m = Integer.parseInt(headerParts[1]);
            this.k = Integer.parseInt(headerParts[2]);

            if (verbose)
                LOGGER.log(Level.INFO, "{0} {1} {2}", new Object[] { n, m, k });

            graph = new Graph(n);

            for (int i = 0; i < m; i++) {
                String edgeLine = br.readLine().trim();
                String[] edgeLineParts = edgeLine.split(" ");
                if (edgeLineParts.length != 3)
                    throw new IllegalStateException("Invalid file edge format: " + edgeLine);

                int u = Integer.parseInt(edgeLineParts[0]);
                int v = Integer.parseInt(edgeLineParts[1]);
                int weight = Integer.parseInt(edgeLineParts[2]);

                if (u <= 0 || u > n || v <= 0 || v > n)
                    throw new IllegalArgumentException("Invalid vertex: " + u + " or " + v);

                graph.addEdge(u, v, weight);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading graph from file", e);
        }
        return graph;
    }

    public int totalVertices() {
        return n;
    }

    public int totalEdges() {
        return m;
    }

    public int totalCenters() {
        return k;
    }
}
