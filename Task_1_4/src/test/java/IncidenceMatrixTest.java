import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncidenceMatrixTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new IncidenceMatrix();
    }

    @Test
    void testAddSelfLoop() {
        Graph graph = createGraph();
        graph.addVertex(1);

        graph.addEdge(1, 1);

        assertEquals(List.of(1), graph.getNeighbors(1));
        assertEquals(1, graph.getEdgeCount());
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
