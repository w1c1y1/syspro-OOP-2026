import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyListTest extends GraphTest {
    @Override
    public Graph createGraph() {
        return new AdjacencyList();
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
