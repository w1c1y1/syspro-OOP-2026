import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {
    @Test
    void sortsDirectedAcyclicGraph() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> result = new TopologicalSort().topologicalSort(graph);

        assertEquals(3, result.size());
        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(2) < result.indexOf(3));
    }

    @Test
    void returnsEmptyListForCycle() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        assertEquals(List.of(), new TopologicalSort().topologicalSort(graph));
    }

    @Test
    void sortsEmptyGraph() {
        Graph graph = new AdjacencyList();

        assertEquals(List.of(), new TopologicalSort().topologicalSort(graph));
    }
}
