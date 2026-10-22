import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {
    @TempDir
    Path tempDir;

    @Test
    void testAddVertex() {
        AdjacencyList graph = new AdjacencyList();

        graph.addVertex(1);

        assertTrue(graph.hasVertex(1));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddDuplicateVertex() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);

        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddEdgeWithMissingVertex() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);

        graph.addEdge(1, 2);

        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testRemoveVertex() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        graph.removeVertex(1);

        assertFalse(graph.hasVertex(1));
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testGetNeighborsOfMissingVertex() {
        AdjacencyList graph = new AdjacencyList();

        assertEquals(List.of(), graph.getNeighbors(1));
    }

    @Test
    void testReadFromFile() throws IOException {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(9);
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "2 1\n1 2\n1 2\n");

        graph.readFromFile(file.toString());

        assertEquals(Set.of(1, 2), new HashSet<>(graph.getVertices()));
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasVertex(9));
    }

    @Test
    void testReadFromMissingFile() {
        AdjacencyList graph = new AdjacencyList();

        assertThrows(UncheckedIOException.class,
                () -> graph.readFromFile(tempDir.resolve("missing.txt").toString()));
    }

    @Test
    void testCopy() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        Graph copy = graph.copy();
        copy.removeEdge(1, 2);

        assertTrue(graph.hasEdge(1, 2));
        assertFalse(copy.hasEdge(1, 2));
    }

    @Test
    void testEquals() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertEquals(graph, graph.copy());
    }

    @Test
    void testEqualsNotEqual() {
        AdjacencyList first = new AdjacencyList();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);
        AdjacencyList second = new AdjacencyList();
        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(2, 1);

        assertNotEquals(first, second);
    }

    @Test
    void testToString() {
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertTrue(graph.toString().contains("1 -> [2]"));
    }
}
