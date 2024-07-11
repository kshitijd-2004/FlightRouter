import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BackendPlaceholder implements BackendInterface, ShortestPathInterface {

    /**
     * Load data from file method which returns a default value of true
     *
     * @return True
     * @throws FileNotFoundException
     */
    @Override
    public boolean loadDataFromFile(String filePath) throws FileNotFoundException {
        return true;
    }

    /**
     * Returns an anonymous class of the ShortestPathInterface for the specific case of finding the
     * route between ORD and AUS
     * @param start
     * @param destination
     * @return Anonymous class of ShortestPathInterface in the case of ORD to AUS else returns null
     */
    @Override
    public ShortestPathInterface getShortestRoute(String start, String destination) {
        if (start.equals("ORD") && destination.equals("AUS")) {
            // Define a route for ORD to AUS
            List<String> ordToAusRoute = new ArrayList<>(Arrays.asList("ORD", "DEN", "AUS"));
            List<String> ordToAusMiles = new ArrayList<>(Arrays.asList("100", "150", "200"));

            // Implement ShortestPathInterface using an anonymous class
            return new ShortestPathInterface() {
                @Override
                public List<String> getRoute() {
                    return ordToAusRoute;
                }

                @Override
                public List<String> milesPerSegment() {
                    return ordToAusMiles;
                }

                @Override
                public double getTotalMiles() {
                    return (ordToAusMiles != null) ? ordToAusMiles.stream().mapToInt(Integer::parseInt).sum() : 0;

                }
            };
        } else {
            return null;
        }
    }

    /**
     * Simply returns a string that the original method output would contain
     * @return A basic string
     */
    @Override
    public String getGraphStats() {

        return "Number of airports\nNumber of flights\nThe total number of miles";
    }

    // Implementing methods from ShortestPathInterface
    @Override
    public List<String> getRoute() {
        return null;
    }

    @Override
    public List<String> milesPerSegment() {
        return null;
    }

    @Override
    public double getTotalMiles() {
        return 0.0;
    }
}

