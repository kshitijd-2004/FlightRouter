import java.io.FileNotFoundException;

/**
 * Interface for the backend that takes an instance of the GraphADT as a constructor parameter
 * and exposes functionality to the frontend:
 * -read data from a file
 * -get the shortest route from a start to a destination airport in the dataset
 * -get a string with statistics about the dataset that includes the number of airports,flights,
 * and the total miles for all edges in the graph
 */

public interface BackendInterface {
    //private GraphADT graph = null;

    //Constructor
    //public IndividualBackendInterface(GraphADT graph){this.graph = graph}


    //reads data and initialize graph
    boolean loadDataFromFile(String filePath) throws FileNotFoundException;

    //gets shortest route from a start to a destination airport
    ShortestPathInterface getShortestRoute(String start, String destination);


    //get statistics about the dataset
    String getGraphStats();

}
