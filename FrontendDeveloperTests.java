import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class FrontendDeveloperTests {

    /**
     * Test for whether the file was loaded or not
     */
    @Test
    public void loadFileTest() {
        String input = "1\nflight.dot\n";
        TextUITester test = new TextUITester(input);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.loadFile();

        String output = test.checkOutput();

        Assertions.assertTrue(output.contains("Enter file path: "));
        Assertions.assertTrue(output.contains("File has been loaded"));
    }

    /**
     * Tester method that tests the showStats method
     */
    @Test
    public void showStatisticsTest() {
        String input = "2\n";
        TextUITester test = new TextUITester(input);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.showStats();

        String output = test.checkOutput();


        Assertions.assertTrue(output.contains("Number of airports"));
        Assertions.assertTrue(output.contains("Number of flights"));
        Assertions.assertTrue(output.contains("The total number of miles"));
    }

    /**
     * Test that tests the showRouteInfo method. For this specific test the start airport is
     * ORD and destination is AUS with DEN in the route. The test also checks the number of miles
     * per segment and the total number of miles
     */
    @Test
    public void shortestRouteTest() {
        String input = "3\nORD\nAUS\n";
        TextUITester test = new TextUITester(input);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.showRouteInfo("ORD", "AUS");

        String output = test.checkOutput();


        Assertions.assertTrue(output.contains("Shortest route from ORD to AUS:"));
        Assertions.assertTrue(output.contains("Route: [ORD, DEN, AUS]"));
        Assertions.assertTrue(output.contains("Miles per segment: [100, 150, 200]"));
        Assertions.assertTrue(output.contains("Total miles: 450"));
    }

    /**
     * Test that checks whether the correct output line is given when
     * the user exits the application
     */
    @Test
    public void exitTest() {
        String input = "4\n";
        TextUITester test = new TextUITester(input);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.exitApplication();
        String output = test.checkOutput();

        Assertions.assertTrue(output.contains("Exiting application"));
    }

    /**
     * Test that checks whether the correct output is given when invalid airports are inputted
     * while finding the shortest route between two airports.
     */
    @Test
    public void shortestRouteInvalidInputTest() {
        String input = "3\nDEN\nDFW\n";
        TextUITester test = new TextUITester(input);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.showRouteInfo("DEN", "DFW");

        String output = test.checkOutput();

        Assertions.assertTrue(output.contains("No route found between DEN and DFW"));
    }

    // Integration Tests

    /**
     * Integration test that uses an actual implementation of the Backend to test the showRouteInfo method
     */
    @Test
    public void integrationTestShowRouteInfo() {
        String input = "3\nORD\nAUS\n"; // Simulate user input for airports
        TextUITester test = new TextUITester(input);

        // Assuming you have a populated graph with necessary information
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendClass backend = new BackendClass(graph);
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        // Insert necessary data into the graph (nodes and edges)
        graph.insertNode("ORD");
        graph.insertNode("MSN");
        graph.insertNode("AUS");
        graph.insertNode("DEN");
        graph.insertEdge("MSN", "ORD", 135);
        graph.insertEdge("ORD", "DEN", 886);
        graph.insertEdge("DEN", "AUS", 917);

        frontend.showRouteInfo("ORD", "AUS"); // Obtain and display the route info

        String output = test.checkOutput();


        // Add assertions to validate the output contains expected information
        Assertions.assertTrue(output.contains("Shortest route from ORD to AUS:"));
        Assertions.assertTrue(output.contains("Route: [ORD, DEN, AUS]"));
        Assertions.assertTrue(output.contains("Miles per segment: [ORD - DEN: 886, DEN - AUS: 917]"));
        Assertions.assertTrue(output.contains("Total miles: 1803.0"));
    }



    /**
     * Integration test that uses an actual implementation of the backend to test the getStats method
     */
    @Test
    public void integrationTestGetStats() {

        String input = "2\n";
        TextUITester test = new TextUITester(input);
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendClass backend = new BackendClass(graph);
        Frontend frontend = new Frontend(backend, scanner);

        // Insertion of airports (nodes) and paths (edges) and number of miles (edge weights)

        // Insertion of airports (or nodes)
        graph.insertNode("ORD");
        graph.insertNode("MSN");
        graph.insertNode("AUS");
        graph.insertNode("DEN");

        // Insertion of paths (or edges) as long as with the number of miles (edge weights)
        graph.insertEdge("MSN", "ORD", 135);
        graph.insertEdge("ORD", "DEN", 886);
        graph.insertEdge("DEN", "AUS", 917);

        frontend.showStats();
        String output = test.checkOutput();


        Assertions.assertTrue(output.contains("Number of airports: 4"));
        Assertions.assertTrue(output.contains("Number of flights: 3"));
        Assertions.assertTrue(output.contains("The total number of miles: "));
    }

    // Backend Tests

    /**
     * Checks if backend gives the correct valid route
     * @throws FileNotFoundException
     */
    @Test
    public void backendTestValidRoute() throws FileNotFoundException {
        DijkstraGraph<String,Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendClass tester = new BackendClass(graph);
        tester.loadDataFromFile("flights.dot");
        ShortestPathClass route = (ShortestPathClass) tester.getShortestRoute("LAX","SAN");
        Assertions.assertEquals(route.getTotalMiles(),109);
    }
    
    /**
     * Checks if the backend successfully loads the file
     */
    @Test
    public void backendTestLoadFile() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendClass backend = new BackendClass(graph);

        try {
            boolean loadedSuccessfully = backend.loadDataFromFile("flights.dot");
            Assertions.assertTrue(loadedSuccessfully);
        } catch (FileNotFoundException e) {
            Assertions.fail("File not found");
        }
    }

}


