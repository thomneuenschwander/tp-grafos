package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import algoritmhs.AdjacencyMatrix;

public class GraphReader {
    private static final Logger LOGGER = Logger.getLogger(GraphReader.class.getName());

    public static AdjacencyMatrix read(String filePath) {
        AdjacencyMatrix graph = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine().trim();
            if (header == null || header.isEmpty()) 
                throw new IllegalStateException("Empty header");

            String[] headerParts = header.split(" ");
            if (headerParts.length < 3) 
                throw new IllegalStateException("Invalid file header. Expected: 'n m k'");

            int n = Integer.parseInt(headerParts[0]); 
            int m = Integer.parseInt(headerParts[1]); 
            int k = Integer.parseInt(headerParts[2]); 

            LOGGER.log(Level.INFO, "Vértices = {0}, Arestas = {1}, Clusters = {2}",
                    new Object[] { n, m, k });

            graph = new AdjacencyMatrix(n, m, k);

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

                graph.addEdge(v, w, weight);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading graph from file", e);
        }
        return graph;
    }
}
