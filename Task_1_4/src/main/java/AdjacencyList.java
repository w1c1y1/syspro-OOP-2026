import java.util.*;

/**
 * Adjacency list implementation of a directed graph.
 */
public class AdjacencyList extends AbstractGraph {
    private Map<Integer, List<Integer>> adjacency;
    private int edgeCount;

    public AdjacencyList() {
        this.adjacency = new HashMap<>();
        this.edgeCount = 0;
    }

    @Override
    public Graph createEmpty() {
        return new AdjacencyList();
    }

    @Override
    public void addVertex(int vertex) {
        adjacency.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        if (!adjacency.containsKey(vertex)) {
            return;
        }
        edgeCount -= adjacency.get(vertex).size();
        adjacency.remove(vertex);
        for (List<Integer> neighbors : adjacency.values()) {
            if (neighbors.remove(Integer.valueOf(vertex))) {
                edgeCount--;
            }
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (adjacency.containsKey(from) && adjacency.containsKey(to)
                && !adjacency.get(from).contains(to)) {
            adjacency.get(from).add(to);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (adjacency.containsKey(from)
                && adjacency.get(from).remove(Integer.valueOf(to))) {
            edgeCount--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return new ArrayList<>(adjacency.getOrDefault(vertex, new ArrayList<>()));
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(adjacency.keySet());
    }

    @Override
    public boolean hasEdge(int from, int to) {
        return adjacency.containsKey(from) && adjacency.get(from).contains(to);
    }

    @Override
    public boolean hasVertex(int vertex) {
        return adjacency.containsKey(vertex);
    }

    @Override
    public int getVertexCount() {
        return adjacency.size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdjacencyList:\n");
        for (int v : getVertices()) {
            sb.append(v).append(" -> ").append(getNeighbors(v)).append("\n");
        }
        return sb.toString();
    }
}