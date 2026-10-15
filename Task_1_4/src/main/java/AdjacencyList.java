import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyList implements Graph {
    private Map<Integer, List<Integer>> adjacency;
    private int edgeCount;

    public AdjacencyList() {
        this.adjacency = new HashMap<>();
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        adjacency.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        if (adjacency.containsKey(vertex)) {
            edgeCount -= adjacency.get(vertex).size();
            adjacency.remove(vertex);
            for (List<Integer> neighbors : adjacency.values()) {
                if (neighbors.remove(Integer.valueOf(vertex))) {
                    edgeCount--;
                }
            }
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (adjacency.containsKey(from) && adjacency.containsKey(to) &&
                !adjacency.get(from).contains(to)) {
            adjacency.get(from).add(to);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (adjacency.containsKey(from) &&
                adjacency.get(from).remove(Integer.valueOf(to))) {
            edgeCount--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacency.getOrDefault(vertex, new ArrayList<>());
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
    public Graph copy() {
        AdjacencyList copy = new AdjacencyList();
        for (int v : getVertices()) {
            copy.addVertex(v);
        }
        for (int v : getVertices()) {
            for (int neighbor : getNeighbors(v)) {
                copy.addEdge(v, neighbor);
            }
        }
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Graph)) return false;
        Graph other = (Graph) obj;
        if (getVertexCount() != other.getVertexCount() ||
                getEdgeCount() != other.getEdgeCount()) {
            return false;
        }
        for (int v : getVertices()) {
            if (!other.hasVertex(v)) {
                return false;
            }
            List<Integer> neighbors = getNeighbors(v);
            List<Integer> otherNeighbors = other.getNeighbors(v);
            if (neighbors.size() != otherNeighbors.size()) {
                return false;
            }
            for (int n : neighbors) {
                if (!other.hasEdge(v, n)) {
                    return false;
                }
            }
        }
        return true;
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
