// --== CS400 File Header Information ==--
// Name: Kshitij Dhande
// Email: kdhande@wisc.edu
// Group and Team: G21
// Group TA: Robert Nagel
// Lecturer: Florian Heimerl

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // Priority queue to greedily explore shorter path possibilities
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();

        // Map to keep track of visited nodes and their shortest path costs
        MapADT<NodeType, Double> visitedNodes = new PlaceholderMap<>();

        // Start node
        Node startNode = nodes.get(start);
        if (startNode == null) {
            throw new NoSuchElementException("Start node not found in the graph.");
        }

        // Initialize priority queue with the start node
        priorityQueue.add(new SearchNode(startNode, 0.0, null));

        // Dijkstra's Algorithm
        while (!priorityQueue.isEmpty()) {
            SearchNode current = priorityQueue.poll();

            // Check if the current node has been visited
            if (visitedNodes.containsKey(current.node.data)) {
                continue;  // Skip this node if it has already been visited
            }

            // Mark the current node as visited
            visitedNodes.put(current.node.data, current.cost);

            if (current.node.data.equals(end)) {
                return current; // Shortest path found
            }

            // Explore neighbors and update costs in the priority queue
            for (Edge edge : current.node.edgesLeaving) {
                Node successor = edge.successor;
                double newCost = current.cost + edge.data.doubleValue();

                if (!visitedNodes.containsKey(successor.data) || newCost < visitedNodes.get(successor.data)) {
                    priorityQueue.add(new SearchNode(successor, newCost, current));
                }
            }
        }

        // No path found
        throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
    }





    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // Compute the shortest path using Dijkstra's algorithm
        SearchNode endNode;
        try {
            endNode = computeShortestPath(start, end);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        // Initialize a list to store the data values of nodes along the shortest path
        List<NodeType> pathData = new LinkedList<>();
        // Traverse the path backward, starting from the end node
        while (endNode != null) {
            // Add the data value of the current node to the beginning of the list
            pathData.add(0, endNode.node.data);
            // Move to the predecessor node in the path
            endNode = endNode.predecessor;
        }
        // Return the list of data values representing the shortest path
        return pathData;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode endNode;
        try {
            endNode = computeShortestPath(start, end);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        if (endNode == null) {
            // No path found
            return Double.NaN;
        }

        // Calculate the cost of the shortest path
        return endNode.cost;
    }

    /**
     * Test that checks the nodes in the shortest path
     */
    @Test
    public void test1(){
        PlaceholderMap map = new PlaceholderMap<>();
        BaseGraph<String, Double> graph = new BaseGraph<>(map);
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "C", 2.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("B", "D", 1.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("D", "F", 0.0);
        graph.insertEdge("F", "D", 2.0);
        graph.insertEdge("F", "H", 4.0);
        graph.insertEdge("G", "H", 4.0);
        graph.insertEdge("A", "E", 15.0);



        DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(map);

        List<String> shortestPath = dijkstraGraph.shortestPathData("A", "E");

        assertEquals(List.of("A", "B", "D", "E"), shortestPath);
    }

    /**
     * Test that checks the cost and sequence of data from a start and end point
     */
    @Test
    public void test2() {
        PlaceholderMap map = new PlaceholderMap<>();
        BaseGraph<String, Double> graph = new BaseGraph<>(map);
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "C", 2.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("B", "D", 1.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("D", "F", 0.0);
        graph.insertEdge("F", "D", 2.0);
        graph.insertEdge("F", "H", 4.0);
        graph.insertEdge("G", "H", 4.0);
        graph.insertEdge("A", "E", 15.0);

        DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(map);

        List<String> shortestPath = dijkstraGraph.shortestPathData("A", "F");
        double shortestPathCost = dijkstraGraph.shortestPathCost("A", "F");

        assertEquals(5.0, shortestPathCost, 0.1);
        assertEquals(List.of("A", "B", "D", "F"), shortestPath);
    }


    /**
     * Test that checks if exception is being thrown if there's no direct path
     */
    @Test
    public void test3() {
        PlaceholderMap map = new PlaceholderMap<>();
        BaseGraph<String, Double> graph = new BaseGraph<>(map);
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "C", 2.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("B", "D", 1.0);
        graph.insertEdge("C", "D", 5.0);
        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("D", "F", 0.0);
        graph.insertEdge("F", "D", 2.0);
        graph.insertEdge("F", "H", 4.0);
        graph.insertEdge("G", "H", 4.0);
        graph.insertEdge("A", "E", 15.0);

        DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(map);

        assertThrows(NoSuchElementException.class, () -> dijkstraGraph.shortestPathData("A", "G"));



    }
}

