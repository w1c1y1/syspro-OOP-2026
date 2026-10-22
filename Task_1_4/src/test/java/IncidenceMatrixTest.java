import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixTest {
    @TempDir
    Path tempDir;

    @Test
    void testAddVertex() {
        IncidenceMatrix graph = new IncidenceMatrix();

        graph.addVertex(1);

        assertTrue(graph.hasVertex(1));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1));
        assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    void testAddEdgeWithMissingVertex() {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);

        graph.addEdge(1, 2);

        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testAddSelfLoop() {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);

        graph.addEdge(1, 1);

        assertEquals(List.of(1), graph.getNeighbors(1));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testRemoveVertex() {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.removeVertex(1);

        assertFalse(graph.hasVertex(1));
        assertTrue(graph.hasEdge(2, 3));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testGetNeighborsOfMissingVertex() {
        IncidenceMatrix graph = new IncidenceMatrix();

        assertEquals(List.of(), graph.getNeighbors(1));
    }

    @Test
    void testReadFromFile() throws IOException {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(9);
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "2 1\n1 2\n1 2\n");

        graph.readFromFile(file.toString());

        assertEquals(List.of(1, 2), graph.getVertices());
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasVertex(9));
    }

    @Test
    void testReadFromMissingFile() {
        IncidenceMatrix graph = new IncidenceMatrix();

        assertThrows(UncheckedIOException.class,
                () -> graph.readFromFile(tempDir.resolve("missing.txt").toString()));
    }

    @Test
    void testCopy() {
        IncidenceMatrix graph = new IncidenceMatrix();
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
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertEquals(graph, graph.copy());
    }

    @Test
    void testEqualsNotEqual() {
        IncidenceMatrix first = new IncidenceMatrix();
        first.addVertex(1);
        first.addVertex(2);
        first.addEdge(1, 2);
        IncidenceMatrix second = new IncidenceMatrix();
        second.addVertex(1);
        second.addVertex(2);
        second.addEdge(2, 1);

        assertNotEquals(first, second);
    }

    @Test
    void testToString() {
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertEquals("IncidenceMatrix:\ne(1,2) \n1: 1 \n2: -1 \n", graph.toString());
    }
}
