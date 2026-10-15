import java.util.List;

/**
 * Public interface for other types of graph's storage.
 */
public interface Graph {
    /**
     * Add's vertex to graph.
     * @param vertex we need to add.
     */
    void addVertex(int vertex);

    /**
     * Removes vertex from graph.
     * @param vertex we need to remove.
     */
    void removeVertex(int vertex);

    /**
     * Adds edge from one vertex to another.
     * @param from start vertex.
     * @param to end vertex.
     */
    void addEdge(int from, int to);

    /**
     * Removes edge between "from" and "to" vertexes.
     * @param from start vertex.
     * @param to end vertex.
     */
    void removeEdge(int from, int to);

    /**
     * Gets neighbors of a given vertex.
     * @param vertex we need to get neighbors for.
     * @return
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Return of vertices of a graph.
     * @return list on vertices.
     */
    List<Integer> getVertices();

    /**
     * Checks if edge between two vertexes exist.
     * @param from start vertex.
     * @param to end vertex.
     * @return is edge exists.
     */
    boolean hasEdge(int from, int to);

    /**
     * Checks if vertex exist.
     * @param vertex we need to check.
     * @return is in graph or not.
     */
    boolean hasVertex(int vertex);

    /**
     * Returns number of vertexes.
     * @return number of vertexes.
     */
    int getVertexCount();

    /**
     * Method to get number of edges in current graph.
     * @return number of edges.
     */
    int getEdgeCount();

    /**
     * Makes cope of a current graph.
     * @return identical new graph.
     */
    Graph copy();
}
