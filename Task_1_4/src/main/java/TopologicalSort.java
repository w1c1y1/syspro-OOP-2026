import java.util.*;

/**
 * Implements Topological sort on oriented graph.
 * Does it with different graph implementations.
 */
public class TopologicalSort {
    /**
     * Takes graph as an argument and sorts it.
     *
     * @param graph we need to sort.
     *              Must be inherited from Graph interface.
     * @return list of vertexes (integers) in sorted order.
     */
    public List<Integer> topologicalSort(Graph graph) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int v : graph.getVertices()) inDegree.put(v, 0);
        for (int v : graph.getVertices()) {
            for (int n : graph.getNeighbors(v)) {
                inDegree.merge(n, 1, Integer::sum);
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (Map.Entry<Integer, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0) queue.offer(e.getKey());
        }
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            result.add(v);
            for (int n : graph.getNeighbors(v)) {
                int deg = inDegree.merge(n, -1, Integer::sum);
                if (deg == 0) queue.offer(n);
            }
        }
        if (result.size() != graph.getVertexCount()) return new ArrayList<>();
        return result;
    }
}