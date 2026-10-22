import java.io.*;
import java.util.*;

/**
 * Abstract method for Graph's implementations.
 */
public abstract class AbstractGraph implements Graph {
    /**
     * Creates empty graph.
     *
     * @return empty graph.
     */
    abstract public Graph createEmpty();

    @Override
    public void readFromFile(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            for (int v : new ArrayList<>(getVertices())) {
                removeVertex(v);
            }
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
        Graph copy = createEmpty();
        for (int v : getVertices()) {
            copy.addVertex(v);
        }
        for (int v : getVertices()) {
            for (int n : getNeighbors(v)) {
                copy.addEdge(v, n);
            }
        }
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }
        if (getVertexCount() != other.getVertexCount()) {
            return false;
        }
        if (getEdgeCount() != other.getEdgeCount()) {
            return false;
        }
        for (int v : getVertices()) {
            if (!other.hasVertex(v)) {
                return false;
            }
            List<Integer> neighbors = getNeighbors(v);
            if (neighbors.size() != other.getNeighbors(v).size()) {
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
}