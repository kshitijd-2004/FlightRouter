import java.io.FileNotFoundException;
import java.util.Scanner;

public class Frontend implements FrontendInterface {

    Scanner scnr;
    BackendInterface backend;

    /**
     * Constructor for Frontend class
     */
    public Frontend(BackendInterface backend, Scanner scnr) {
        this.scnr = scnr;
        this.backend = backend;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendClass backend = new BackendClass(graph);
        Frontend frontend = new Frontend(backend, scnr);
        frontend.startApplication();
    }

    /**
     * Starts the main command loop for the user, allowing them to interact with the application.
     */
    @Override
    public void startApplication() {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Load File\n2. Show Stats\n3. Show Route Info\n4. Exit");
            int choice = scnr.nextInt();
            scnr.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    loadFile();
                    break;
                case 2:
                    showStats();
                    break;
                case 3:
                    System.out.print("Enter start airport: ");
                    String start = scnr.nextLine();
                    System.out.print("Enter destination airport: ");
                    String destination = scnr.nextLine();
                    showRouteInfo(start, destination);
                    break;
                case 4:
                    exitApplication();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    /**
     * Prompts the user to specify and load a data file.
     */
    @Override
    public void loadFile() {
        try {
            System.out.print("Enter file path: ");
            String filePath = scnr.nextLine();
            boolean loaded = backend.loadDataFromFile(filePath);
            if (loaded) {
                System.out.println("File has been loaded");
            } else {
                System.out.println("Failed to load file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found");
        }
    }

    /**
     * Displays statistics about the dataset, including the number of airports,
     * the number of flights, and the total number of miles.
     */
    @Override
    public void showStats() {
        String stats = backend.getGraphStats();
        System.out.println(stats);
    }

    /**
     * Asks the user for a start and destination airport, then lists the shortest route
     * between those airports, including all airports on the way, the distance for each segment,
     * and the total number of miles from the start to the destination airport.
     *
     * @param start       - Start airport
     * @param destination - Destination airport
     */
    @Override
    public void showRouteInfo(String start, String destination) {
        ShortestPathInterface shortestPath = backend.getShortestRoute(start, destination);

        if (shortestPath != null) {
            System.out.println("Shortest route from " + start + " to " + destination + ":");
            System.out.println("Route: " + shortestPath.getRoute());
            System.out.println("Miles per segment: " + shortestPath.milesPerSegment());
            System.out.println("Total miles: " + shortestPath.getTotalMiles());
        } else {
            System.out.println("No route found between " + start + " and " + destination);
        }
    }

    /**
     * Exits the application.
     */
    @Override
    public void exitApplication() {
        System.out.println("Exiting application");
    }
}

