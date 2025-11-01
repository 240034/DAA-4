package graph;

public class Metrics {
    private long startTime;
    private long endTime;

    public int kahnPushes = 0;
    public int kahnPops = 0;

    public long dfsVisits = 0;
    public long dfsEdges = 0;
    public long pushes = 0;
    public long pops = 0;
    public long relaxations = 0;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public double getDurationMs() {
        return(endTime - startTime) / 1000000.0;
    }

    public void reset() {
        dfsVisits = dfsEdges = pushes = pops = relaxations = 0;
        startTime = endTime = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Time: %.3f ms | DFS visits: %d | Edges: %d | Pushes: %d | Pops: %d | Relax: %d",
                getDurationMs(), dfsVisits, dfsEdges, pushes, pops, relaxations
        );
    }
}
