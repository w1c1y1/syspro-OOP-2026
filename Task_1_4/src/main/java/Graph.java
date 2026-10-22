import java.util.*;

public interface Graph {

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, the graph is unchanged.
     * @param vertex vertex to add.
     */
    void addVertex(int vertex);

    /**
     * Removes the vertex and all edges incident to it
     * incoming and outgoing, including self-loops.
     * If the vertex does not exist, the graph is unchanged.
     * @param vertex vertex to remove.
     */
    void removeVertex(int vertex);

    /**
     * Adds a directed edge from one vertex to another.
     * Self-loops are allowed.
     * If the edge already exists, or endpoint does not exist, the graph is unchanged.
     * @param from start vertex.
     * @param to end vertex.
     */
    void addEdge(int from, int to);

    /**
     * Removes the directed edge between two vertices.
     * If the edge does not exist, the graph is unchanged.
     * @param from start vertex.
     * @param to end vertex.
     */
    void removeEdge(int from, int to);

    /**
     * Returns the neighbors of the given vertex.
     * If the vertex does not exist, returns an empty list.
     * @param vertex vertex which neighbors we want to get.
     * @return list of neighbor vertices.
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Returns all vertices of the graph.
     * @return list of vertices.
     */
    List<Integer> getVertices();

    /**
     * Checks whether an edge from one vertex to another exists.
     * If either vertex does not exist, returns false.
     * @param from start vertex.
     * @param to end vertex.
     * @return true if the edge exists, false if not.
     */
    boolean hasEdge(int from, int to);

    /**
     * Checks whether the vertex belongs to the graph.
     * @param vertex vertex to check.
     * @return true if the vertex exists, false if not.
     */
    boolean hasVertex(int vertex);

    /**
     * Returns the number of vertices in the graph.
     * @return vertex count.
     */
    int getVertexCount();

    /**
     * Returns the number of edges in the graph.
     * @return edge count.
     */
    int getEdgeCount();

    /**
     * Replaces the content of the graph with the content of the file.
     * File format: First line - two integers separated by space.
     * First V (vertex count) and E (edge count),
     * then V vertex (nonnegative int's),
     * then 2*E pairs "from to" describing each edge.
     * @param fileName path to the input file.
     * @throws java.io.UncheckedIOException if the file cannot be read.
     */
    void readFromFile(String fileName);

    /**
     * Creates an independent copy of the graph.
     * @return new graph with the same vertices and edges.
     */
    Graph copy();
}