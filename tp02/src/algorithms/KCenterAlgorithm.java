package algorithms;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KCenterAlgorithm {
    void initialize(int k);

    void computeCenters();

    int getRadius();

    Set<Integer> getCenters();

    Map<Integer, List<Integer>> getClusters();
}
