import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new AdjacencyMatrix(2);
    }

    @Test
    void testAddVertexExpandsCapacity() {
        AdjacencyMatrix graph = new AdjacencyMatrix(1);

        graph.addVertex(3);

        assertTrue(graph.hasVertex(3));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddNegativeVertex() {
        Graph graph = createGraph();

        graph.addVertex(-1);

        assertFalse(graph.hasVertex(-1));
        assertEquals(0, graph.getVertexCount());
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
