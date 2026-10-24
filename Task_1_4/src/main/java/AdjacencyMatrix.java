import java.util.*;

/**
 * Adjacency matrix implementation of a directed graph.
 */
public class AdjacencyMatrix extends AbstractGraph {
    private int[][] matrix;
    private boolean[] vertices;
    private int vertexCount;
    private int edgeCount;
    private int capacity;

    /**
     * Constructs an adjacency matrix with the given initial capacity.
     *
     * @param capacity initial capacity.
     */
    public AdjacencyMatrix(int capacity) {
        this.capacity = Math.max(1, capacity);
        this.matrix = new int[this.capacity][this.capacity];
        this.vertices = new boolean[this.capacity];
        this.vertexCount = 0;
        this.edgeCount = 0;
    }

    @Override
    public Graph createEmpty() {
        return new AdjacencyMatrix(capacity);
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= capacity) {
            return;
        }
        int newCapacity = Math.max(minCapacity, capacity * 2);
        int[][] newMatrix = new int[newCapacity][newCapacity];
        boolean[] newVertices = new boolean[newCapacity];
        for (int i = 0; i < capacity; i++) {
            newVertices[i] = vertices[i];
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, capacity);
        }
        matrix = newMatrix;
        vertices = newVertices;
        capacity = newCapacity;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex < 0) {
            return;
        }
        ensureCapacity(vertex + 1);
        if (!vertices[vertex]) {
            vertices[vertex] = true;
            vertexCount++;
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= capacity || !vertices[vertex]) {
            return;
        }
        vertices[vertex] = false;
        vertexCount--;
        for (int i = 0; i < capacity; i++) {
            if (matrix[vertex][i] == 1) {
                edgeCount--;
            }
            if (matrix[i][vertex] == 1 && i != vertex) {
                edgeCount--;
            }
            matrix[vertex][i] = 0;
            matrix[i][vertex] = 0;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from < 0 || from >= capacity || to < 0 || to >= capacity) {
            return;
        }
        if (!vertices[from] || !vertices[to] || matrix[from][to] == 1) {
            return;
        }
        matrix[from][to] = 1;
        edgeCount++;
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from < 0 || from >= capacity || to < 0 || to >= capacity) {
            return;
        }
        if (matrix[from][to] == 1) {
            matrix[from][to] = 0;
            edgeCount--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> result = new ArrayList<>();
        if (vertex < 0 || vertex >= capacity || !vertices[vertex]) {
            return result;
        }
        for (int i = 0; i < capacity; i++) {
            if (matrix[vertex][i] == 1) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public List<Integer> getVertices() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (vertices[i]) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public boolean hasEdge(int from, int to) {
        return from >= 0 && from < capacity && to >= 0 && to < capacity
                && matrix[from][to] == 1;
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertex >= 0 && vertex < capacity && vertices[vertex];
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdjacencyMatrix:\n");
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}