import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.List;

public class Junit {

    //grafo de ejemplo del libro (CentroDeGrafo.pdf, Example 6.11)
    private Graph buildTestGraph()
    {
        Graph g = new Graph();
        g.addEdge("a", "b", 1);
        g.addEdge("b", "c", 2);
        g.addEdge("b", "d", 1);
        g.addEdge("c", "d", 2);
        g.addEdge("c", "e", 4);
        g.addEdge("d", "e", 5);
        g.addEdge("d", "c", 3);
        return g;
    }

    @Test
    public void testAddCity()
    {
        Graph g = new Graph();
        g.addCity("Guatemala");
        assertEquals(1, g.size());
    }

    @Test
    public void testNoDuplicateCity()
    {
        Graph g = new Graph();
        g.addCity("Antigua");
        g.addCity("Antigua");
        assertEquals(1, g.size());
    }

    @Test
    public void testAddEdgeCreatesVertices()
    {
        Graph g = new Graph();
        g.addEdge("Mixco", "Antigua", 30);
        assertEquals(2, g.size());
    }

    @Test
    public void testAddEdgeWeight()
    {
        Graph g = new Graph();
        g.addEdge("Mixco", "Antigua", 30);
        int[][] m = g.getMatrix();
        assertEquals(30, m[g.getIndex("Mixco")][g.getIndex("Antigua")]);
    }

    @Test
    public void testRemoveEdge()
    {
        Graph g = buildTestGraph();
        g.removeEdge("a", "b");
        int[][] m = g.getMatrix();
        assertEquals(Graph.INF, m[g.getIndex("a")][g.getIndex("b")]);
    }

    @Test
    public void testRemoveEdgeNotFound()
    {
        Graph g = buildTestGraph();
        assertFalse(g.removeEdge("a", "z"));
    }

    @Test
    public void testFloydDistanceDirect()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        assertEquals(1, f.getDistance("a", "b"));
    }

    @Test
    public void testFloydDistanceViaIntermediate()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        //a->b->d = 1+1 = 2
        assertEquals(2, f.getDistance("a", "d"));
    }

    @Test
    public void testFloydNoPath()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        assertEquals(Graph.INF, f.getDistance("b", "a"));
    }

    @Test
    public void testFloydSameCity()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        assertEquals(0, f.getDistance("a", "a"));
    }

    @Test
    public void testFloydPath()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        List<String> path = f.getPath("a", "d");
        assertEquals(List.of("a", "b", "d"), path);
    }

    @Test
    public void testFloydPathNoRoute()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        assertTrue(f.getPath("e", "a").isEmpty());
    }

    @Test
    public void testCenterNotA()
    {
        Algorithm f = new Algorithm(buildTestGraph());
        //"a" no tiene arcos entrantes, no puede ser centro
        assertFalse("a".equals(f.getCenter()));
    }

    @Test
    public void testSmallGraph()
    {
        Graph g = new Graph();
        g.addEdge("Mixco", "Antigua", 30);
        g.addEdge("Antigua", "Escuintla", 25);
        g.addEdge("Escuintla", "SantaLucia", 15);
        Algorithm f = new Algorithm(g);
        assertEquals(70, f.getDistance("Mixco", "SantaLucia"));
    }

    @Test
    public void testRemoveAndRecalculate()
    {
        Graph g = buildTestGraph();
        g.removeEdge("a", "b");
        Algorithm f = new Algorithm(g);
        assertEquals(Graph.INF, f.getDistance("a", "b"));
    }
}