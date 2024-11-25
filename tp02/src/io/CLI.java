package io;

import algorithms.KCenterAlgorithm;

public class CLI {
    private String filePath;
    private KCenterAlgorithm.Type algorithm;
    private boolean verbose;

    public CLI(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("No arguments provided.");

        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] split = arg.substring(2).split("=", 2);
                switch (split[0].toLowerCase()) {
                    case "file" -> this.filePath = split.length == 2 ? split[1] : null;
                    case "algorithm" -> this.algorithm = parseAlgorithm(split.length == 2 ? split[1] : null);
                    case "verbose" -> this.verbose = true;
                    default -> throw new IllegalArgumentException("Invalid option: " + arg);
                }
            } else if (arg.equals("-v"))
                this.verbose = true;
            else if (arg.equals("1"))
                this.algorithm = KCenterAlgorithm.Type.BruteForce;
            else if (arg.equals("2"))
                this.algorithm = KCenterAlgorithm.Type.Greedy;
            else
                this.filePath = arg;
        }

        if (this.filePath == null)
            throw new IllegalArgumentException(
                    "File path is required. Use --file=<file_path> or pass the file path directly.");

        if (this.algorithm == null)
            throw new IllegalArgumentException(
                    "Algorithm is required. Use 1 (BruteForce), 2 (Greedy), or --algorithm=<BruteForce|Greedy>.");
    }

    private KCenterAlgorithm.Type parseAlgorithm(String algorithm) {
        return switch (algorithm == null ? "" : algorithm.toLowerCase()) {
            case "bruteforce" -> KCenterAlgorithm.Type.BruteForce;
            case "greedy" -> KCenterAlgorithm.Type.Greedy;
            default -> throw new IllegalArgumentException(
                    "Invalid algorithm. Choose 'BruteForce', 'Greedy', 1 (BruteForce), or 2 (Greedy).");
        };
    }

    public String getFilePath() {
        return filePath;
    }

    public KCenterAlgorithm.Type getAlgorithm() {
        return algorithm;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public static void usage() {
        System.err.println("""
                Usage: java Main <file_path> [1|2|-v|--verbose|--algorithm=<BruteForce|Greedy>]
                Options:
                    <file_path>              Path to the input file (required).
                    1                        Use BruteForce algorithm.
                    2                        Use Greedy algorithm.
                    --algorithm=<algorithm>  Specify algorithm: 'BruteForce' or 'Greedy'.
                    -v, --verbose            Enable verbose mode.
                """);
    }
}
