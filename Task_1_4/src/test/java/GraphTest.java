import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class GraphTest {
    @TempDir
    Path tempDir;

    protected abstract Graph createGraph();

    @Test
    void testAddVertex() {
        Graph graph = createGraph();

        graph.addVertex(1);

        assertTrue(graph.hasVertex(1));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddDuplicateVertex() {
        Graph graph = createGraph();
        graph.addVertex(1);

        graph.addVertex(1);

        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        Graph graph = createGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddEdgeWithMissingVertex() {
        Graph graph = createGraph();
        graph.addVertex(1);

        graph.addEdge(1, 2);

        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testRemoveVertex() {
        Graph graph = createGraph();
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
        Graph graph = createGraph();

        assertEquals(List.of(), graph.getNeighbors(1));
    }

    @Test
    void testReadFromFile() throws Exception {
        Graph graph = createGraph();
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
        Graph graph = createGraph();

        assertThrows(UncheckedIOException.class,
                () -> graph.readFromFile(tempDir.resolve("missing.txt").toString()));
    }

    @Test
    void testCopy() {
        Graph graph = createGraph();
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
        Graph graph = createGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertEquals(graph, graph.copy());
    }

    @Test
    void testEqualsNotEqual() {
        Graph first = createGraph();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);
        Graph second = createGraph();
        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(2, 1);

        assertNotEquals(first, second);
    }
}
