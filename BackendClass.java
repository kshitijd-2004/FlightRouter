

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;
public class BackendClass implements BackendInterface{

    private DijkstraGraph<String,Integer> graph;
    private ShortestPathClass shortestPath;

    private int totalMiles;



    public BackendClass(DijkstraGraph<String,Integer> graphADT){
        this.graph = graphADT;
    }

    /**
     * Reads data from file, and populates the graph field
     * @return true if file load was successful and graph populated
     * @throws FileNotFoundException if file is not found
     */
    @Override
    public boolean loadDataFromFile(String filePath) throws FileNotFoundException {
            Scanner scanner = new Scanner(new File(filePath));
            Pattern pattern = Pattern.compile("\"([A-Z]{3})\" -- \"([A-Z]{3})\" \\[miles=(\\d+)\\];");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String startAirport = matcher.group(1);
                    String destinationAirport = matcher.group(2);
                    int miles = Integer.parseInt(matcher.group(3));

                    this.graph.insertNode(startAirport);
                    this.graph.insertNode(destinationAirport);
                    this.graph.insertEdge(startAirport, destinationAirport, miles);
                    totalMiles += miles;
                }
            }
            scanner.close();
            return true;
        }


        /**
         * A method that returns a shortestPathClass/Interface with information about the shortest
         * route from a start to a destination airport
         * @param start
         * @param destination
         * @return ShortestPathInterface instance that we can call its methods on to get specific
         * statistics from
         */
    @Override
    public ShortestPathInterface getShortestRoute(String start, String destination) {
        this.shortestPath = new ShortestPathClass(start, destination, this.graph);
        return this.shortestPath;
    }

    /**
     * get a string with statistics about the dataset that includes the number of nodes (airports),
     * the number of edges (flights), and the total miles (sum of weights) for all edges in the graph.
     * @return a String
     */
    @Override
    public String getGraphStats() {

        int edgeCount = graph.getEdgeCount();
        int numberOfNodes = graph.getNodeCount();


        String toReturn =
                "Number of airports: " +  numberOfNodes + ", Number of flights: " + edgeCount +
                        ", The total number of miles: " + this.totalMiles;
        return toReturn;
    }

}

