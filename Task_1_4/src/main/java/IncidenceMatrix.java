import java.io.*;
import java.util.*;

/**
 * Incidence matrix implementation of a directed graph.
 */
public class IncidenceMatrix implements Graph {
    private List<Integer> vertices;
    private Map<Integer, Integer> vertexToRow;
    private List<int[]> edges;
    private List<List<Integer>> matrix;

    /**
     * Constructs an empty incidence matrix graph.
     */
    public IncidenceMatrix() {
        this.vertices = new ArrayList<>();
        this.vertexToRow = new HashMap<>();
        this.edges = new ArrayList<>();
        this.matrix = new ArrayList<>();
    }

    @Override
    public void addVertex(int vertex) {
        if (vertexToRow.containsKey(vertex)) {
            return;
        }
        int row = vertices.size();
        vertices.add(vertex);
        vertexToRow.put(vertex, row);
        List<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            newRow.add(0);
        }
        matrix.add(newRow);
    }

    @Override
    public void removeVertex(int vertex) {
        Integer rowObj = vertexToRow.remove(vertex);
        if (rowObj == null) {
            return;
        }
        int row = rowObj;
        vertices.remove(row);
        matrix.remove(row);
        for (int i = row; i < vertices.size(); i++) {
            vertexToRow.put(vertices.get(i), i);
        }
        for (int col = edges.size() - 1; col >= 0; col--) {
            int[] e = edges.get(col);
            if (e[0] == vertex || e[1] == vertex) {
                removeEdgeAt(col);
            }
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (!vertexToRow.containsKey(from) || !vertexToRow.containsKey(to)) {
            return;
        }
        if (hasEdge(from, to)) {
            return;
        }
        edges.add(new int[]{from, to});
        int fromRow = vertexToRow.get(from);
        int toRow = vertexToRow.get(to);
        for (int i = 0; i < vertices.size(); i++) {
            List<Integer> row = matrix.get(i);
            if (from == to && i == fromRow) {
                row.add(2);
            } else if (i == fromRow) {
                row.add(1);
            } else if (i == toRow) {
                row.add(-1);
            } else {
                row.add(0);
            }
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        for (int col = 0; col < edges.size(); col++) {
            int[] e = edges.get(col);
            if (e[0] == from && e[1] == to) {
                removeEdgeAt(col);
                return;
            }
        }
    }

    private void removeEdgeAt(int col) {
        edges.remove(col);
        for (List<Integer> row : matrix) {
            row.remove(col);
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> result = new ArrayList<>();
        Integer row = vertexToRow.get(vertex);
        if (row == null) {
            return result;
        }
        for (int col = 0; col < edges.size(); col++) {
            int val = matrix.get(row).get(col);
            if (val == 2) {
                result.add(vertex);
            } else if (val == 1) {
                result.add(edges.get(col)[1]);
            }
        }
        return result;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        for (int[] e : edges) {
            if (e[0] == from && e[1] == to) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertexToRow.containsKey(vertex);
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    @Override
    public int getEdgeCount() {
        return edges.size();
    }

    @Override
    public void readFromFile(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            vertices.clear();
            vertexToRow.clear();
            edges.clear();
            matrix.clear();
            int v = sc.nextInt();
            int e = sc.nextInt();
            for (int i = 0; i < v; i++) {
                addVertex(sc.nextInt());
            }
            for (int i = 0; i < e; i++) {
                addEdge(sc.nextInt(), sc.nextInt());
            }
        } catch (FileNotFoundException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public Graph copy() {
        IncidenceMatrix copy = new IncidenceMatrix();
        for (int v : vertices) {
            copy.addVertex(v);
        }
        for (int[] e : edges) {
            copy.addEdge(e[0], e[1]);
        }
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) obj;
        if (getVertexCount() != other.getVertexCount()) {
            return false;
        }
        if (getEdgeCount() != other.getEdgeCount()) {
            return false;
        }
        for (int v : vertices) {
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
        StringBuilder sb = new StringBuilder("IncidenceMatrix:\n");
        for (int[] e : edges) {
            sb.append("e(").append(e[0]).append(",").append(e[1]).append(") ");
        }
        sb.append("\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < edges.size(); j++) {
                sb.append(matrix.get(i).get(j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}