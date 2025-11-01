package graph;

import java.util.*;

public class Graph {

    private final Map<String, Set<String>> adj = new HashMap<>();
    private final Set<String> nodes = new HashSet<>();

    public void addNode(String v) {
        nodes.add(v);
        adj.putIfAbsent(v, new HashSet<>());
    }

    public void addEdge(String u, String v) {
        addNode(u);
        addNode(v);
        adj.get(u).add(v);
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public Set<String> getNeighbors(String v) {
        return adj.getOrDefault(v, Collections.emptySet());
    }

    public List<String> getAllEdges() {
        List<String> edges = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adj.entrySet()) {
            String u = entry.getKey();
            for (String v : entry.getValue()) {
                edges.add(u + "->" + v);
            }
        }
        return edges;
    }
}
