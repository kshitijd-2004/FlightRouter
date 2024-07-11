import java.util.ArrayList;
import java.util.List;

/**
 * Interface for a class that stores the results of a shortest path search.
 */
public interface ShortestPathInterface {

    //  String start;
    //  String destination;
    //  public ShortestPathBD(String start, String destination){
    //    this.start = start;
    //    this.destination = destination;
    //  }
    List<String> getRoute(); //returns the list of airports
    // along the route

    List<String> milesPerSegment(); //returns a list of the
    // miles to travel for each segment of
    // the
    // route

    double getTotalMiles(); //return total miles for the route

}

