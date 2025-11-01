package graph.scc;

import graph.Graph;
import graph.Metrics;
import java.util.List;
import graph.topo.KahnTopologicalSort;


public class TarjanDemo {
    public static void main(String[] args) {
        Graph g = new Graph();


        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A");
        g.addEdge("B", "D");
        g.addEdge("E", "F");
        g.addEdge("E", "F");
        g.addEdge("F", "E");
        g.addNode("G");


        Metrics metrics = new Metrics();
        TarjanSCC tarjan = new TarjanSCC(g, metrics);
        List<List<String>> comps = tarjan.run();

        System.out.println("SCCs found: " + comps.size());
        for (int i = 0; i < comps.size(); i++) {
            System.out.println("C" + i + ": " + comps.get(i));
        }

        System.out.println("\nMetrics:");
        System.out.println(metrics);

        var condensation = CondensationBuilder.buildCondensation(g, comps);
        System.out.println("\nCondensation DAG:");
        for (String c : condensation.getNodes()) {
            System.out.println(c + " -> " + condensation.getNeighbors(c));
        }

        KahnTopologicalSort topo = new KahnTopologicalSort(condensation, metrics);
        List<String> order = topo.sort();

        System.out.println("\nTopological order of components:");
        System.out.println(order);
    }



}
