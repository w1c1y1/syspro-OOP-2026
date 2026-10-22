import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixTest {
    @TempDir
    Path tempDir;

    @Test
    void testAddVertexExpandsCapacity() {
        AdjacencyMatrix graph = new AdjacencyMatrix(1);

        graph.addVertex(3);

        assertTrue(graph.hasVertex(3));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddNegativeVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);

        graph.addVertex(-1);

        assertFalse(graph.hasVertex(-1));
        assertEquals(0, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        graph.addVertex(1);

        graph.addEdge(0, 1);

        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddEdgeWithMissingVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);

        graph.addEdge(0, 1);

        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testRemoveVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        graph.removeVertex(0);

        assertFalse(graph.hasVertex(0));
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testGetNeighborsOfMissingVertex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);

        assertEquals(List.of(), graph.getNeighbors(1));
    }

    @Test
    void testReadFromFile() throws IOException {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "2 1\n1 2\n1 2\n");

        graph.readFromFile(file.toString());

        assertEquals(List.of(1, 2), graph.getVertices());
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasVertex(0));
    }

    @Test
    void testReadFromMissingFile() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);

        assertThrows(UncheckedIOException.class,
                () -> graph.readFromFile(tempDir.resolve("missing.txt").toString()));
    }

    @Test
    void testCopy() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        Graph copy = graph.copy();
        copy.removeEdge(0, 1);

        assertTrue(graph.hasEdge(0, 1));
        assertFalse(copy.hasEdge(0, 1));
    }

    @Test
    void testEquals() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        assertEquals(graph, graph.copy());
    }

    @Test
    void testEqualsNotEqual() {
        AdjacencyMatrix first = new AdjacencyMatrix(2);
        first.addVertex(0);
        first.addVertex(1);
        first.addEdge(0, 1);
        AdjacencyMatrix second = new AdjacencyMatrix(2);
        second.addVertex(0);
        second.addVertex(1);
        second.addEdge(1, 0);

        assertNotEquals(first, second);
    }

    @Test
    void testToString() {
        AdjacencyMatrix graph = new AdjacencyMatrix(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        assertEquals("AdjacencyMatrix:\n0 1 \n0 0 \n", graph.toString());
    }
}
